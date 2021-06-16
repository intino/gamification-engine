package io.intino.gamification.core.box.helper;

import io.intino.gamification.core.box.events.achievement.AchievementType;
import io.intino.gamification.core.graph.Achievement;
import io.intino.gamification.core.graph.Player;

import java.util.List;

public class AchievementEntry {

    private final Achievement achievement;
    private final String contextId;
    private final AchievementType type;
    private final List<Player> players;

    public AchievementEntry(Achievement achievement, String contextId, AchievementType type, List<Player> players) {
        this.achievement = achievement;
        this.contextId = contextId;
        this.type = type;
        this.players = players;
    }

    public Achievement achievement() {
        return achievement;
    }

    public String context() {
        return contextId;
    }

    public AchievementType type() {
        return type;
    }

    public List<Player> players() {
        return players;
    }
}
