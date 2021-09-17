package io.intino.gamification.graph.model;

import io.intino.gamification.util.Log;
import io.intino.gamification.util.data.Progress;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static io.intino.gamification.util.data.Progress.State.*;

public class Player extends Actor {

    private final Map<String, Progress> achievementProgress;

    public Player(String worldId, String id) {
        super(worldId, id);
        this.achievementProgress = new HashMap<>();
    }

    public final Progress achievementProgress(String achievementId) {
        Achievement achievement = world().achievements().find(achievementId);
        if(achievement == null) {
            NoSuchElementException e = new NoSuchElementException("Achievement " + achievementId + " does not exist");
            Log.error(e.getMessage(), e);
            throw e;
        }
        return achievementProgress.computeIfAbsent(achievementId, id -> new Progress(achievement.total()));
    }

    public final Collection<Map.Entry<String, Progress>> achievementProgress() {
        return achievementProgress.entrySet();
    }

    public final List<String> completedAchievements() {
        return achievementProgress().stream().filter(entry -> entry.getValue().state() == Complete)
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public final List<String> failedAchievements() {
        return achievementProgress().stream().filter(entry -> entry.getValue().state() == Failed)
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public final List<String> inProgressAchievements() {
        return achievementProgress().stream().filter(entry -> entry.getValue().state() == InProgress)
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public final void assignMission(String missionId, Instant expirationTime) {
        Match match = availableMatch();
        if(match != null) {
            match.player(id()).assignMission(missionId, expirationTime);
        }
    }

    public void failMission(String missionId) {
        Match match = availableMatch();
        if(match != null) {
            match.player(id()).failMission(missionId);
        }
    }

    public void completeMission(String missionId) {
        Match match = availableMatch();
        if(match != null) {
            match.player(id()).completeMission(missionId);
        }
    }

    public void cancelMission(String missionId) {
        Match match = availableMatch();
        if(match != null) {
            match.player(id()).cancelMission(missionId);
        }
    }

    private Match availableMatch() {
        if(!world().isAvailable()) return null;
        Match match = world().currentMatch();
        if(match == null || !match().isAvailable()) return null;
        return match;
    }
}
