package io.intino.gamification.graph;

import io.intino.gamification.core.GamificationCore;
import io.intino.gamification.graph.model.World;
import io.intino.gamification.util.Log;
import io.intino.gamification.util.file.FileUtils;
import io.intino.gamification.util.serializer.Binary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
        graph.worlds().stream().parallel().forEach(this::saveWorld);
    }

    private void saveWorld(World world) {
        File file = FileUtils.createFile(rootDirectory + "/world#" + world.id() + ".gmf");
        File temp = new File(file.getAbsolutePath() + ".tmp");
        Binary.write(world, temp);
        try {
            Files.move(temp.toPath(), file.toPath(), REPLACE_EXISTING, ATOMIC_MOVE);
            Files.delete(temp.toPath());
        } catch (IOException e) {
            Log.error(e);
        }
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
}
