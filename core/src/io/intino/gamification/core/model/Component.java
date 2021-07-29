package io.intino.gamification.core.model;

public abstract class Component {

    protected final String id;

    protected Component(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
