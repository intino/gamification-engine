package io.intino.gamification.model;

import io.intino.gamification.data.Progress;

import java.util.*;
import java.util.stream.Collectors;

public class Player extends Actor {

    private final Map<String, Progress> achievementProgress;

    public Player(String world, String id) {
        super(world, id);
        this.achievementProgress = new LinkedHashMap<>();
    }

    public Progress getAchievementProgress(String achievementId) {
        return achievementProgress.computeIfAbsent(achievementId, id -> new Progress());
    }

    public Collection<Map.Entry<String, Progress>> achievementProgress() {
        return achievementProgress.entrySet();
    }

    public List<String> completedAchievements() {
        return achievementProgress().stream().filter(entry -> entry.getValue().complete())
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public List<String> failedAchievements() {
        return achievementProgress().stream().filter(entry -> entry.getValue().failed())
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public List<String> inProgressAchievements() {
        return achievementProgress().stream().filter(entry -> !entry.getValue().failed() && !entry.getValue().complete())
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }
}
