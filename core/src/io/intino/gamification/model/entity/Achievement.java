package io.intino.gamification.model.entity;

public class Achievement extends Entity {

    private final String description;   //Obligatorio definirlo
    private final int maxCount;         //Obligatorio definirlo

    public Achievement() {
        this.description = null;
        this.maxCount = 0;
    }
}
