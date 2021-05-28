package io.intino.gamification.core.graph.rules;

import io.intino.magritte.lang.model.Rule;

public enum AchievementType implements Rule<Enum> {

    Local, Global;

    @Override
    public boolean accept(Enum value) {
        return value instanceof AchievementType;
    }
}
