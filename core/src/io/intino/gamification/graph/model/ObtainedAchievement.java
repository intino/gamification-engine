package io.intino.gamification.graph.model;

import java.io.Serializable;
import java.time.Instant;

public final class ObtainedAchievement implements Comparable<ObtainedAchievement>, Serializable {

    private final String achievementId;
    private final String playerId;
    private final Instant instant;

    public ObtainedAchievement(String achievementId, String playerId, Instant instant) {
        this.achievementId = achievementId;
        this.playerId = playerId;
        this.instant = instant;
    }

    /*public String achievement() {
        return this.achievement;
    }

    public String actorId() {
        return this.actorId;
    }

    public Instant instant() {
        return this.instant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObtainedAchievement that = (ObtainedAchievement) o;
        return Objects.equals(achievement, that.achievement) && Objects.equals(actorId, that.actorId) && Objects.equals(instant, that.instant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(achievement, actorId, instant);
    }

    @Override
    public String toString() {
        return "ObtainedAchievement{" +
                "achievement='" + achievement + '\'' +
                ", actorId='" + actorId + '\'' +
                ", instant=" + instant +
                '}';
    }*/

    @Override
    public int compareTo(ObtainedAchievement o) {
        return instant.compareTo(o.instant);
    }
}
