package io.intino.gamification.core.model;

import io.intino.gamification.core.box.checkers.CheckerHandler;
import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.model.attributes.MissionDifficulty;
import io.intino.gamification.core.model.attributes.MissionType;

import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;

public class Mission {

    private final String id;
    private final String description;
    private final MissionType type;
    private final MissionDifficulty difficulty;
    private final Instant expiration;
    private final EventType eventInvolved;
    private final int maxCount;
    private final List<String> playersId;
    private final Consumer<CheckerHandler.Checker<? extends GamificationEvent>> progressCosumer;

    public Mission(io.intino.gamification.core.graph.Mission mission) {
        this.id = mission.id();
        this.description = mission.description();
        this.type = mission.type();
        this.difficulty = mission.difficulty();
        this.expiration = mission.expiration();
        this.eventInvolved = mission.eventInvolved();
        this.maxCount = mission.maxCount();
        this.playersId = mission.players();
        this.progressCosumer = checker -> CheckerHandler.progressIf(mission, checker);
    }

    public String id() {
        return id;
    }

    public String description() {
        return description;
    }

    public MissionType type() {
        return type;
    }

    public MissionDifficulty difficulty() {
        return difficulty;
    }

    public Instant expiration() {
        return expiration;
    }

    public EventType eventInvolved() {
        return eventInvolved;
    }

    public int maxCount() {
        return maxCount;
    }

    public List<String> playersId() {
        return playersId;
    }

    public <T extends GamificationEvent> void progressIf(CheckerHandler.Checker<T> checker) {
        progressCosumer.accept(checker);
    }
}