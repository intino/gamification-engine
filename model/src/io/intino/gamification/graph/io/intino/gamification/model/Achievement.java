package io.intino.gamification.graph.io.intino.gamification.model;

public class Achievement extends Resource {

    private final String description;   //Obligatorio definirlo
    private final int maxCount;         //Obligatorio definirlo

    public Achievement() {
        this.description = null;
        this.maxCount = 0;
    }
}
