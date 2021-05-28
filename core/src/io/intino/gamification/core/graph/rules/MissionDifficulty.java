package io.intino.gamification.core.graph.rules;

import io.intino.magritte.lang.model.Rule;

public enum MissionDifficulty implements Rule<Enum> {

    Easy, Medium, Hard;

    @Override
    public boolean accept(Enum value) {
        return value instanceof MissionDifficulty;
    }
}
