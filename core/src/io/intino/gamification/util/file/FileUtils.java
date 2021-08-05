package io.intino.gamification.util.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

    public static File createFile(String filePath) {
        File file = new File(filePath);

        createFolder(file.getParentFile().getAbsolutePath());

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                //TODO REGISTRAR ERROR
                e.printStackTrace();
            }
        }

        return file;
    }

    public static File createFolder(String folderPath) {
        File folder = new File(folderPath);
        if(!folder.exists()) folder.mkdirs();
        return folder;
    }

    public static void appendIn(File file, String text) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.append(text).close();
        } catch (IOException e) {
            //TODO REGISTRAR ERROR
            e.printStackTrace();
        }
    }

    public static String read(File file) {
        String content = "";
        try {
            content = Files.readString(file.toPath());
        } catch (IOException e) {
            //TODO REGISTRAR ERROR
            e.printStackTrace();
        }
        return content;
    }

    public static void write(File file, String text) {
        try {
            Files.writeString(file.toPath(), text);
        } catch (IOException e) {
            //TODO REGISTRAR ERROR
            e.printStackTrace();
        }
    }
}
