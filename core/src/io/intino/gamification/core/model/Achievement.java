package io.intino.gamification.core.model;

import io.intino.gamification.core.box.checkers.CheckerHandler;
import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.GamificationEvent;

import java.util.function.Consumer;

public class Achievement {

    private final String id;
    private final String description;
    private final EventType eventInvolved;
    private final int maxCount;
    private final Consumer<CheckerHandler.Checker<? extends GamificationEvent>> progressConsumer;

    public Achievement(io.intino.gamification.core.graph.Achievement achievement) {
        this.id = achievement.id();
        this.description = achievement.description();
        this.eventInvolved = achievement.eventInvolved();
        this.maxCount = achievement.maxCount();
        this.progressConsumer = checker -> CheckerHandler.progressIf(achievement, checker);
    }

    public String id() {
        return id;
    }

    public String description() {
        return description;
    }

    public EventType eventInvolved() {
        return eventInvolved;
    }

    public int maxCount() {
        return maxCount;
    }

    public <T extends GamificationEvent> void progressIf(CheckerHandler.Checker<T> checker) {
        progressConsumer.accept(checker);
    }
}