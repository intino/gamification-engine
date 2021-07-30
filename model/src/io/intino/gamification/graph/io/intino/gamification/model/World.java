package io.intino.gamification.graph.io.intino.gamification.model;

import java.util.ArrayList;
import java.util.List;

public class World extends Resource {

    private final Match activeMatch;
    private final List<Match> finishedMatches;
    private final List<Player> players;
    private final List<Item> items;
    private final List<Npc> npcs;
    private final List<Achievement> achievements;

    public World() {
        this.activeMatch = null;
        this.finishedMatches = new ArrayList<>();
        this.players = new ArrayList<>();
        this.items = new ArrayList<>();
        this.npcs = new ArrayList<>();
        this.achievements = new ArrayList<>();
    }
}
