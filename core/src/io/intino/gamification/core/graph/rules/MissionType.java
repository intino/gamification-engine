package io.intino.gamification.core.graph.rules;

import io.intino.magritte.lang.model.Rule;

public enum MissionType implements Rule<Enum> {

    Primary, Secondary, Spacial;

    @Override
    public boolean accept(Enum value) {
        return value instanceof MissionType;
    }
}
