package io.intino.gamification.model;

public class Achievement extends Node {

    private final String description;

    public Achievement(String id, String description) {
        super(id);
        this.description = description;
    }

    public String description() {
        return description;
    }
}
