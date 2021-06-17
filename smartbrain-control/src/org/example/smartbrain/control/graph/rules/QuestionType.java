package org.example.smartbrain.control.graph.rules;

import io.intino.magritte.lang.model.Rule;

public enum QuestionType implements Rule<Enum<QuestionType>> {

    Language, Memory, Math;

    @Override
    public boolean accept(Enum value) {
        return value instanceof QuestionType;
    }
}
