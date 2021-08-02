package io.intino.gamification.utils.file;

import io.intino.gamification.utils.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils extends Util {

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

    public List<String> read(File file) {
        List<String> lines = new ArrayList<>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) lines.add(line);
            br.close();
        } catch (IOException e) {
            //TODO REGISTRAR ERROR
            e.printStackTrace();
        }
        return lines;
    }
}
