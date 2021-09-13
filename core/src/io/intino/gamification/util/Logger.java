package io.intino.gamification.util;

import static io.intino.gamification.util.time.TimeUtils.currentInstant;

public class Logger {

    //TODO: Guardar en file y mejorar
    public static void error(Exception e) {
        System.out.println("[GAMIFICATION-ERROR]");
        System.out.println("ts: " + currentInstant());
        System.out.println("message: " + e.getMessage());
        System.out.println();
    }

    //TODO: Guardar en file y mejorar
    public static void info(String info) {
        System.out.println("[GAMIFICATION-INFO]");
        System.out.println("ts: " + currentInstant());
        System.out.println("message: " + info);
        System.out.println();
    }
}
