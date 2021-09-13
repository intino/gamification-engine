package io.intino.gamification.graph;

import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.graph.model.World;
import io.intino.gamification.util.serializer.Binary;
import io.intino.gamification.util.file.FileUtils;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.intino.gamification.util.time.TimeUtils.currentInstant;

public class GraphSerializer {

    private static final String GRAPH_SUBDIR = "/graph";

    private final GamificationCore core;
    private final GamificationGraph graph;
    private final File rootDirectory;
    private Instant lastSave;

    public GraphSerializer(GamificationCore core) {
        this.core = core;
        this.graph = core.graph();
        this.rootDirectory = FileUtils.createFolder(core.configuration().gamificationPath.get() + GRAPH_SUBDIR);
        this.lastSave = currentInstant();
    }

    public void save() {
        this.lastSave = currentInstant();
        graph.worlds().stream().parallel().forEach(this::saveWorld);
        graph.shouldSave(false);
    }

    private void saveWorld(World world) {
        File file = FileUtils.createFile(rootDirectory + "/world#" + world.id() + ".gmf");
        Binary.write(world, file);
    }

    public void load() {
        File[] worldFiles = rootDirectory.listFiles(this::isWorldFile);
        if(worldFiles == null) return;
        List<World> worlds = Collections.synchronizedList(new ArrayList<>(worldFiles.length));
        Arrays.stream(worldFiles).parallel().forEach(worldFile -> load(worldFile, worlds));
        worlds.forEach(graph.worlds()::add);
    }

    private void load(File worldFile, List<World> worlds) {
        worlds.add(Binary.read(worldFile));
    }

    private boolean isWorldFile(File file) {
        return file.getName().startsWith("world#") && file.getName().endsWith(".gmf");
    }

    public void checkCronSave() {
        if(matchCron()) graph.shouldSave(true);
    }

    private boolean matchCron() {
        return core.configuration().savingCron.get().matches(lastSave);
    }
}
