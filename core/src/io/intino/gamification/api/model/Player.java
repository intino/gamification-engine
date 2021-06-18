package io.intino.gamification.api.model;

import java.util.List;
import java.util.stream.Collectors;

public class Player {

    private final String id;
    private final String worldId;
    private final boolean enabled;
    private final double health;
    private final List<String> groups;
    private final int score;
    private final List<Item> inventory;

    public Player(io.intino.gamification.core.graph.Player player) {
        this.id = player.id();
        this.worldId = player.worldId();
        this.enabled = player.enabled();
        this.health = player.health();
        this.groups = player.groups();
        this.score = player.score();
        this.inventory = player.inventory().stream().map(Item::new).collect(Collectors.toList());
    }

    public String id() {
        return id;
    }

    public String worldId() {
        return worldId;
    }

    public boolean enabled() {
        return enabled;
    }

    public double health() {
        return health;
    }

    public List<String> groups() {
        return groups;
    }

    public int score() {
        return score;
    }

    public List<Item> inventory() {
        return inventory;
    }

    private static class AchievementState {

        private final String achievementId;
        private final io.intino.gamification.core.box.events.achievement.AchievementState state;
        private final int count;

        public AchievementState(io.intino.gamification.core.graph.AchievementState achievementState) {
            this.achievementId = achievementState.achievementId();
            this.state = achievementState.state();
            this.count = achievementState.count();
        }

        public String missionId() {
            return achievementId;
        }

        public io.intino.gamification.core.box.events.achievement.AchievementState state() {
            return state;
        }

        public int count() {
            return count;
        }
    }
}