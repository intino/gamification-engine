package io.intino.gamification.serialization;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.graph.model.Competition;
import io.intino.gamification.graph.model.Round;
import io.intino.gamification.graph.model.Season;
import io.intino.gamification.util.Log;
import io.intino.gamification.util.serializer.Json;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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
public class GamificationSerializer {

    private final File root;
    private boolean prettyPrinting = true;

    public GamificationSerializer(File root) {
        this.root = root;
    }

    public File root() {
        return root;
    }

    public GamificationSerializer prettyPrinting(boolean usePrettyJson) {
        this.prettyPrinting = usePrettyJson;
        return this;
    }

    public synchronized void save(GamificationGraph graph) {
        for(Competition competition : graph.competitions()) {
            save(competition, true);
        }
    }

    public synchronized void save(Competition competition, boolean saveChildren) {
        File directory = competitionDirectory(competition);
        directory.mkdirs();
        File file = competitionFile(competition);
        File tmp = new File(file.getAbsolutePath() + ".tmp");
        try {
            Json.write(competition, tmp, prettyPrinting);
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

    public synchronized void save(Season season, boolean saveChildren) {
        File directory = seasonDirectory(season);
        directory.mkdirs();
        File file = seasonFile(season);
        File tmp = new File(file.getAbsolutePath() + ".tmp");
        try {
            Json.write(season, tmp, prettyPrinting);
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

    public synchronized void save(Round round) {
        File file = roundFile(round);
        file.getParentFile().mkdirs();
        File tmp = new File(file.getAbsolutePath() + ".tmp");
        try {
            Json.write(round, tmp, prettyPrinting);
            Files.move(tmp.toPath(), file.toPath(), REPLACE_EXISTING, ATOMIC_MOVE);
        } catch (Exception e) {
            Log.error(e);
        } finally {
            tmp.delete();
        }
    }

    public GamificationGraph loadGraph() {
        GamificationGraph graph = new GamificationGraph();
        if(!root.exists()) return graph;

        File[] competitions = root.listFiles(f -> f.isDirectory() && f.getName().startsWith("competition"));
        if(competitions == null) return graph;
        Arrays.sort(competitions);

        for(File competitionDir : competitions) {
            File competitionFile = new File(competitionDir, competitionDir.getName() + ".json");
            if(!competitionFile.exists()) continue;
            graph.competitions().add(loadCompetition(competitionFile));
        }
        return graph;
    }

    public Competition loadCompetition(File file) {
        Competition competition = Json.read(Competition.class, file);
        if(competition == null) return null;

        File[] seasons = file.getParentFile().listFiles(f -> f.isDirectory() && f.getName().startsWith("season"));
        if(seasons == null) return competition;
        Arrays.sort(seasons);

        for(File seasonDir : seasons) {
            File seasonFile = new File(seasonDir, seasonDir.getName() + ".json");
            if(!seasonFile.exists()) continue;
            competition.seasons().add(loadSeason(seasonFile));
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
        return new File(root, "competition." + competition.id());
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
