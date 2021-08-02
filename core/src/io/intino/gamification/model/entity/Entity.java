package io.intino.gamification.model.entity;

import java.util.UUID;

public abstract class Entity {

    private final String id;

    public Entity() {
        this.id = UUID.randomUUID().toString();
    }

    public String id() {
        return id;
    }
}
