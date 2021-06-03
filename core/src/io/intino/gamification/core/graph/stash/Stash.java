package io.intino.gamification.core.graph.stash;

import java.util.Arrays;

public enum Stash {

    Gamification, World, Match, Mission, MissionState, Entity, EntityState; //Achievement, AchievementState;

    public static String main() {
        return Gamification.name();
    }

    public static String[] stashes() {
        return Arrays.stream(values()).map(Enum::name).toArray(String[]::new);
    }
}
