package io.intino.gamification.graph.io.intino.gamification.model;

import java.util.UUID;

public abstract class Resource {

    private final String id;

    public Resource() {
        this.id = UUID.randomUUID().toString();
    }

    public String id() {
        return id;
    }
}
