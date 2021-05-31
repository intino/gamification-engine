package io.intino.gamification.core.graph.rules;

import io.intino.magritte.lang.model.Rule;

public enum Type implements Rule<Enum> {

    Player, Npc;

    @Override
    public boolean accept(Enum value) {
        return value instanceof Type;
    }
}
