package io.intino.gamification.util.serializer;

import io.intino.gamification.graph.model.World;
import io.intino.gamification.util.file.FileUtils;

import java.io.File;

public class Binary {

    public static World read(File file) {
        Object object = FileUtils.readObject(file);
        return object != null ? (World) object : null;
    }

    public static void write(World world, File file) {
        FileUtils.writeObject(file, world);
    }
}
