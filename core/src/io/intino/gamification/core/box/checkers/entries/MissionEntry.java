package io.intino.gamification.core.box.checkers.entries;

import io.intino.gamification.core.graph.Mission;
import io.intino.gamification.core.graph.Player;

import java.util.List;

public class MissionEntry {

    private final Mission mission;
    private final String worldId;
    private final List<Player> players;

    public MissionEntry(Mission mission, String contextId, List<Player> players) {
        this.mission = mission;
        this.worldId = contextId;
        this.players = players;
    }

    public Mission mission() {
        return mission;
    }

    public String world() {
        return worldId;
    }

    public List<Player> players() {
        return players;
    }
}
