package io.intino.gamification.util.file;

import io.intino.gamification.util.Log;

import java.io.*;

public class FileUtils {

    public static File createFile(String filePath) {
        File file = new File(filePath);

        createFolder(file.getParentFile().getAbsolutePath());

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.error(e);
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
            Log.error(e);
        }
        return null;
    }

    public static void writeObject(File file, Object object) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            writer.writeObject(object);
        } catch (Exception e) {
            Log.error(e);
        }
    }
}
