package io.intino.gamification.graph;

import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.graph.model.Competition;
import io.intino.gamification.util.Log;
import io.intino.gamification.util.file.FileUtils;
import io.intino.gamification.util.serializer.Binary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.nio.file.StandardCopyOption.*;

public class GraphSerializer {

    private static final String GRAPH_SUBDIR = "/graph";

    private final GamificationCore core;
    private final GamificationGraph graph;
    private final File rootDirectory;

    public GraphSerializer(GamificationCore core) {
        this.core = core;
        this.graph = core.graph();
        this.rootDirectory = FileUtils.createFolder(core.configuration().gamificationPath.get() + GRAPH_SUBDIR);
    }

    public void save() {
        graph.competitions().stream().parallel().forEach(this::saveCompetition);
    }

    private void saveCompetition(Competition competition) {
        File file = FileUtils.createFile(rootDirectory + "/competition#" + competition.id() + ".gmf");
        File temp = new File(file.getAbsolutePath() + ".tmp");
        Binary.write(competition, temp);
        try {
            Files.move(temp.toPath(), file.toPath(), REPLACE_EXISTING, ATOMIC_MOVE);
            if(temp.exists()) Files.delete(temp.toPath());
        } catch (IOException e) {
            Log.error(e);
        }
    }

    public void load() {
        File[] competitionFiles = rootDirectory.listFiles(this::isCompetitionFile);
        if(competitionFiles == null) return;
        List<Competition> competitions = Collections.synchronizedList(new ArrayList<>(competitionFiles.length));
        Arrays.stream(competitionFiles).parallel().forEach(worldFile -> load(worldFile, competitions));
        competitions.forEach(graph.competitions()::add);
    }

    private void load(File competitionFile, List<Competition> competitions) {
        competitions.add(Binary.read(competitionFile));
    }

    private boolean isCompetitionFile(File file) {
        return file.getName().startsWith("competition#") && file.getName().endsWith(".gmf");
    }
}
