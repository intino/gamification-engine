package io.intino.gamification.graph;

import io.intino.gamification.graph.model.Competition;
import io.intino.gamification.graph.model.Round;
import io.intino.gamification.graph.model.Season;
import io.intino.gamification.util.Log;
import io.intino.gamification.util.serializer.Json;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static java.nio.file.StandardCopyOption.*;

/**
 * + root
 *    + competition1
 *      - competition1.json (definitions, players, etc)
 *      + seasons
 *          +season1
 *              - season1.json (player states)
 *              + rounds
 *                  - round1.json (matches and facts)
 *                  - round2.json (matches and facts)
 *                  - round3.json (matches and facts)
 *
 *    + competition2
 *      ...
 * */
public class GraphSerializer {

    private final File rootDirectory;

    public GraphSerializer(File root) {
        this.rootDirectory = root;
    }

    public void save(GamificationGraph graph) {
        graph.competitions().stream().parallel().forEach(competition -> save(competition, true));
    }

    public void save(Competition competition, boolean saveChildren) {
        File directory = competitionDirectory(competition);
        directory.mkdirs();
        File file = competitionFile(competition);
        File tmp = new File(file.getAbsolutePath() + ".tmp");
        try {
            Json.write(competition, tmp);
            Files.move(tmp.toPath(), file.toPath(), REPLACE_EXISTING, ATOMIC_MOVE);
        } catch (Exception e) {
            Log.error(e);
        } finally {
            tmp.delete();
        }

        if(saveChildren) {
            for(Season season : competition.seasons()) {
                save(season, true);
            }
        }
    }

    public void save(Season season, boolean saveChildren) {
        File directory = seasonDirectory(season);
        directory.mkdirs();
        File file = seasonFile(season);
        File tmp = new File(file.getAbsolutePath() + ".tmp");
        try {
            Json.write(season, tmp);
            Files.move(tmp.toPath(), file.toPath(), REPLACE_EXISTING, ATOMIC_MOVE);
        } catch (Exception e) {
            Log.error(e);
        } finally {
            tmp.delete();
        }

        if(saveChildren) {
            for(Round round : season.rounds()) {
                save(round);
            }
        }
    }

    public void save(Round round) {
        File file = roundFile(round);
        File tmp = new File(file.getAbsolutePath() + ".tmp");
        try {
            Json.write(round, tmp);
            Files.move(tmp.toPath(), file.toPath(), REPLACE_EXISTING, ATOMIC_MOVE);
        } catch (Exception e) {
            Log.error(e);
        } finally {
            tmp.delete();
        }
    }

    public Competition loadCompetition(GamificationGraph graph, File file) {
        Competition competition = Json.read(Competition.class, file);
        if(competition == null) return null;
        graph.competitions().add(competition);

        File[] seasons = file.getParentFile().listFiles(f -> f.isDirectory() && f.getName().startsWith("season"));
        if(seasons == null) return competition;
        Arrays.sort(seasons);

        for(File seasonDir : seasons) {
            File[] files = seasonDir.listFiles(f -> f.isFile() && f.getName().startsWith("season"));
            if(files == null || files.length == 0) continue;
            competition.seasons().add(loadSeason(files[0]));
        }

        return competition;
    }

    public Season loadSeason(File file) {
        Season season = Json.read(Season.class, file);
        if(season == null) return null;
        File[] rounds = file.getParentFile().listFiles(f -> f.isFile() && f.getName().startsWith("round"));
        if(rounds == null) return season;
        Arrays.sort(rounds);
        for(File round : rounds)
            season.rounds().add(loadRound(round));
        return season;
    }

    public Round loadRound(File file) {
        return Json.read(Round.class, file);
    }

    public File competitionDirectory(Competition competition) {
        return new File(rootDirectory, "competition." + competition.id());
    }

    public File competitionFile(Competition competition) {
        return new File(competitionDirectory(competition), "competition." + competition.id() + ".json");
    }

    public File seasonDirectory(Season season) {
        return new File(competitionDirectory(season.competition()), "season." + season.id());
    }

    public File seasonFile(Season season) {
        return new File(seasonDirectory(season), "season." + season.id() + ".json");
    }

    public File roundFile(Round round) {
        return new File(seasonDirectory(round.season()), "round." + round.id() + ".json");
    }
}
