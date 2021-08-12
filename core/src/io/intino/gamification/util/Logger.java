package io.intino.gamification.util;

public class Logger {

    //TODO
    public static void error(Exception e) {
        e.printStackTrace();
    }

    public static void info(String info) {
        System.out.println(info);
    }
}
