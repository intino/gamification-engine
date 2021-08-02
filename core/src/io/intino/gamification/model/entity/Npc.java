package io.intino.gamification.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Npc extends Entity {

    private final boolean enabled;      //Obligatorio definirlo
    private final double health;        //Obligatorio definirlo
    private final List<String> groups;      //Obligatorio definirlo

    public Npc() {
        this.enabled = true;
        this.health = 100;
        this.groups = new ArrayList<>();
    }
}
