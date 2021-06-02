package io.intino.gamification.core.graph.stash;

import java.util.Arrays;

public enum Stash {

    Gamification, Entity, Mission, Achievement, AchievementState, Match;

    public static String main() {
        return Gamification.name();
    }

    public static String[] stashes() {
        return Arrays.stream(values()).map(Enum::name).toArray(String[]::new);
    }
}
