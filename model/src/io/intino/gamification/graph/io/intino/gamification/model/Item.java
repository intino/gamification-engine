package io.intino.gamification.graph.io.intino.gamification.model;

import java.util.ArrayList;
import java.util.List;

public class Item extends Resource {

    private final boolean enabled;      //Obligatorio definirlo
    private final double health;        //Obligatorio definirlo
    private final List<String> groups;  //Obligatorio definirlo

    public Item() {
        this.enabled = true;
        this.health = 100;
        this.groups = new ArrayList<>();
    }
}
