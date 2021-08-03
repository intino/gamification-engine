package io.intino.gamification.graph;

import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.graph.model.World;
import io.intino.gamification.utils.Json;
import io.intino.gamification.utils.file.FileUtils;

import java.io.File;

public class GraphSerializer {

    public static final String GRAPH_SUBDIR = "/graph";
    private final GamificationCore core;
    private final GamificationGraph graph;
    private final File rootDirectory;

    public GraphSerializer(GamificationCore core) {
        this.core = core;
        this.graph = core.graph();
        this.rootDirectory = FileUtils.createFolder(core.configuration().gamificationPath.get() + GRAPH_SUBDIR);
    }

    public void save() {
        for(World world : graph.worlds().getAll()) {
            save(world);
        }
    }

    private void save(World world) {
        File file = FileUtils.createFile(rootDirectory + "/world#" + world.id() + ".json");
        FileUtils.write(file, Json.toJsonPretty(world));
    }

    public void load() {
        File[] worldFiles = rootDirectory.listFiles(this::isWorldFile);
        if(worldFiles == null) return;
        for(File worldFile : worldFiles) {
            load(worldFile);
        }
    }

    private void load(File worldFile) {
        World world = Json.fromJson(FileUtils.read(worldFile), World.class);
        graph.worlds().add(world);
    }

    private boolean isWorldFile(File file) {
        return file.getName().startsWith("world#") && file.getName().endsWith(".json");
    }
}
