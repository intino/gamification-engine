package io.tetrabot.test.serialization;

import io.tetrabot.util.Json;

import java.io.*;
import java.util.*;

public class Serialization_ {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Properties props0 = loadProperties("C:/Users/naits/Desktop/MonentiaDev/props0.properties");
        Properties props1 = loadProperties("C:/Users/naits/Desktop/MonentiaDev/props1.properties");

        Set<Object> entries = new TreeSet<>(props0.values());
        entries.removeAll(props1.values());

        System.out.println(entries);
    }

    private static Properties loadProperties(String filename) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static void write(Hola hola) {
        try(ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("hola.dat"))) {
            writer.writeObject(hola);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Hola read() {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream("hola.dat"))) {
            return (Hola) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class Hola implements Serializable {

        private static final long serialVersionUID = 1;

        private String age = "abc";
        private int property = 1;

        @Override
        public String toString() {
            return Json.toJson(this);
        }
    }
}
