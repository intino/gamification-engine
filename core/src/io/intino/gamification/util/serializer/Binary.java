package io.intino.gamification.util.serializer;

import io.intino.gamification.graph.model.Competition;
import io.intino.gamification.util.file.FileUtils;

import java.io.File;

public class Binary {

    public static Competition read(File file) {
        Object object = FileUtils.readObject(file);
        return object != null ? (Competition) object : null;
    }

    public static void write(Competition competition, File file) {
        FileUtils.writeObject(file, competition);
    }
}
