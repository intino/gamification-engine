package io.intino.gamification.graph.io.intino.gamification.model;

import java.util.ArrayList;
import java.util.List;

public class Player extends Resource {

    private final boolean enabled;      //Obligatorio definirlo
    private final double health;        //Obligatorio definirlo
    private final List<String> groups;  //Obligatorio definirlo
    private final int score;            //Obligatorio definirlo
    private final List<Item> inventory;
    private final List<AchievementState> achievements;
    private final Integer level;

    public Player() {
        this.enabled = false;
        this.health = 100;
        this.groups = new ArrayList<>();
        this.score = 0;
        this.inventory = new ArrayList<>();
        this.achievements = new ArrayList<>();
        this.level = 1;
    }

    public static class AchievementState {

        private final Achievement achievement;
        private final int count;

        public AchievementState() {
            this.achievement = null;
            this.count = 0;
        }
    }
}
