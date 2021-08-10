package io.intino.gamification.graph.model;

import io.intino.gamification.util.data.Progress;

import java.util.*;
import java.util.stream.Collectors;

import static io.intino.gamification.util.data.Progress.State.*;

public class Player extends Actor {

    private final Map<String, Progress> achievementProgress;

    public Player(String world, String id) {
        super(world, id);
        this.achievementProgress = new HashMap<>();
    }

    public Progress getAchievementProgress(String achievementId) {
        Achievement achievement = world().achievements().find(achievementId);
        //TODO REGISTRAR ERROR
        if(achievement == null) throw new NoSuchElementException("Achievement " + achievementId + " does not exist");
        return achievementProgress.computeIfAbsent(achievementId, id -> new Progress(achievement.total()));
    }

    public Collection<Map.Entry<String, Progress>> achievementProgress() {
        return achievementProgress.entrySet();
    }

    public List<String> completedAchievements() {
        return achievementProgress().stream().filter(entry -> entry.getValue().state() == Complete)
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public List<String> failedAchievements() {
        return achievementProgress().stream().filter(entry -> entry.getValue().state() == Failed)
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public List<String> inProgressAchievements() {
        return achievementProgress().stream().filter(entry -> entry.getValue().state() == InProgress)
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }
}
