package io.intino.gamification.test;

import io.intino.gamification.core.graph.Entity;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        Entity.setAttributeListener("test", (entity, oldValue, newValue) -> oldValue, Integer::parseInt);
    }
}

