package io.intino.gamification.util.file;

import io.intino.gamification.graph.model.World;
import io.intino.gamification.util.Logger;

import java.io.*;
import java.nio.file.Files;

public class FileUtils {

    public static File createFile(String filePath) {
        File file = new File(filePath);

        createFolder(file.getParentFile().getAbsolutePath());

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Logger.error(e);
            }
        }

        return file;
    }

    public static File createFolder(String folderPath) {
        File folder = new File(folderPath);
        if(!folder.exists()) folder.mkdirs();
        return folder;
    }

    public static Object readObject(File file) {
        try (ObjectInputStream reader = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            return reader.readObject();
        } catch (Exception e) {
            Logger.error(e);
        }
        return null;
    }

    public static void writeObject(File file, Object object) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            writer.writeObject(object);
        } catch (Exception e) {
            Logger.error(e);
        }
    }
}
