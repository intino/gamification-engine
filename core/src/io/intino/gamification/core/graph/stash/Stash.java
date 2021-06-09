package io.intino.gamification.core.graph.stash;

import java.util.Arrays;

public enum Stash {

    Core, Contexts, Worlds, Matches, Entities, Players, Enemies, Npcs, Items, PlayersState, Missions, MissionsState, Achievements, AchievementsState;

    public static String main() {
        return Core.name();
    }

    public static String[] stashes() {
        return Arrays.stream(values()).map(Enum::name).toArray(String[]::new);
    }
}
