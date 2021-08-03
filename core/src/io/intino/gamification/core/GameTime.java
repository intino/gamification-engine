package io.intino.gamification.core;

public final class GameTime {

    static float deltaTime;
    static long frame;

    public static float deltaTime() {
        return GameTime.deltaTime;
    }

    public static long frame() {
        return GameTime.frame;
    }
}
