package io.intino.gamification.graph;

import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.graph.model.GamificationGraph;
import io.intino.gamification.graph.model.World;
import io.intino.gamification.util.data.Json;
import io.intino.gamification.util.file.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        graph.worlds().stream().parallel().forEach(this::saveWorld);
        graph.shouldSave(false);
    }

    private void saveWorld(World world) {
        File file = FileUtils.createFile(rootDirectory + "/world#" + world.id() + ".json");
        FileUtils.write(file, Json.toJsonPretty(world));
    }

    public void load() {
        File[] worldFiles = rootDirectory.listFiles(this::isWorldFile);
        if(worldFiles == null) return;
        List<World> worlds = Collections.synchronizedList(new ArrayList<>(worldFiles.length));
        Arrays.stream(worldFiles).parallel().forEach(worldFile -> load(worldFile, worlds));
        worlds.forEach(graph.worlds()::add);
    }

    private void load(File worldFile, List<World> worlds) {
        worlds.add(Json.fromJson(FileUtils.read(worldFile), World.class));
    }

    private boolean isWorldFile(File file) {
        return file.getName().startsWith("world#") && file.getName().endsWith(".json");
    }
}
