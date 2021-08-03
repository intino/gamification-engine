package io.intino.gamification.model;

import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.utils.Json;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Datamart {

    private final GamificationCore core;
    private final GamificationGraph graph;
    private final File rootDirectory;

    public Datamart(GamificationCore core) {
        this.core = core;
        this.graph = core.graph();
        this.rootDirectory = new File(""); // TODO: ??
        rootDirectory.mkdirs();
    }

    public void save() {
        for(World world : graph.worlds().getAll()) {
            save(world);
        }
    }

    private void save(World world) {
        File file = new File(rootDirectory, "world#" + world.id() + ".json");
        file.getParentFile().mkdirs();
        try {
            Files.writeString(file.toPath(), Json.toJsonPretty(world));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        File[] worldFiles = rootDirectory.listFiles(this::isWorldFile);
        if(worldFiles == null) return;
        for(File worldFile : worldFiles) {
            load(worldFile);
        }
    }

    private void load(File worldFile) {
        try {
            String json = Files.readString(worldFile.toPath());
            World world = Json.fromJson(json, World.class);
            graph.worlds().add(world);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isWorldFile(File file) {
        return file.getName().startsWith("world#") && file.getName().endsWith(".json");
    }

    public GamificationGraph graph() {
        return graph;
    }

    public File root() {
        return rootDirectory;
    }
}
