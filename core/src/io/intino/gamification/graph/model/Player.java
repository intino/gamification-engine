package io.intino.gamification.graph.model;

import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Player extends Actor {

    private final List<ObtainedAchievement> achievements;

    public Player(String worldId, String id) {
        super(worldId, id);
        this.achievements = new ArrayList<>();
    }

    public final List<ObtainedAchievement> achievements() {
        return Collections.unmodifiableList(achievements);
    }

    public final List<ObtainedAchievement> achievementsOfType(String achievementId) {
        return achievements.stream().filter(a -> a.achievement().equals(achievementId)).collect(Collectors.toList());
    }

    public void addAchievement(String achievementId) {
        achievements.add(new ObtainedAchievement(achievementId, id(), TimeUtils.currentInstant()));
    }

    public void addAchievement(String achievementId, Instant instant) {
        achievements.add(new ObtainedAchievement(achievementId, id(), instant));
        achievements.sort(Comparator.naturalOrder());
    }

    public void removeAchievement(int index) {
        achievements.remove(index);
    }

    public final void assignMission(MissionAssignment missionAssignment) {
        Match match = availableMatch();
        if(match != null) {
            match.player(id()).assignMission(missionAssignment);
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
