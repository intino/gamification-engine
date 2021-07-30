package io.intino.gamification.utils.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {

    public File setFile(String filePath) {
        File file = new File(filePath);

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

    public File setFolder(String folderPath) {
        File folder = new File(folderPath);
        if(!folder.exists()) folder.mkdirs();
        return folder;
    }

    public void appendIn(File file, String text) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.append(text).close();
        } catch (IOException e) {
            //TODO REGISTRAR ERROR
            e.printStackTrace();
        }
    }
}
