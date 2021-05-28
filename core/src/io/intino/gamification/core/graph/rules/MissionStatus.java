package io.intino.gamification.core.graph.rules;

import io.intino.magritte.lang.model.Rule;

public enum MissionStatus implements Rule<Enum> {

    Pending, Completed, Cancelled, Failed;

    @Override
    public boolean accept(Enum value) {
        return value instanceof MissionStatus;
    }
}
