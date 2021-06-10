package org.example.cinepolis.control.graph.rules;

import io.intino.magritte.lang.model.Rule;

public enum AlertImportance implements Rule<Enum> {

    Low, Medium, Important;

    @Override
    public boolean accept(Enum value) {
        return value instanceof AlertImportance;
    }
}
