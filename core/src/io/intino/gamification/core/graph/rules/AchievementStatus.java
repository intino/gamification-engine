package io.intino.gamification.core.graph.rules;

import io.intino.magritte.lang.model.Rule;

public enum AchievementStatus implements Rule<Enum> {

    Achieved, Failed, Pending;

    @Override
    public boolean accept(Enum value) {
        return value instanceof AchievementStatus;
    }
}
