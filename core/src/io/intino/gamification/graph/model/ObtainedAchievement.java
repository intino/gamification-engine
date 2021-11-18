package io.intino.gamification.graph.model;

public class ObtainedAchievement extends Node {

    ObtainedAchievement(String id) {
        super(id);
    }

    @Override
    public final Node parent() {
        return null;
    }
}