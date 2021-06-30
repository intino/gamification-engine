package io.intino.gamification.core.model;

import java.util.List;

public class Npc {

    private final String id;
    private final String worldId;
    private final boolean enabled;
    private final double health;
    private final List<String> groups;

    public Npc(io.intino.gamification.core.graph.Npc npc) {
        this.id = npc.id();
        this.worldId = npc.worldId();
        this.enabled = npc.enabled();
        this.health = npc.health();
        this.groups = npc.groups();
    }

    public String id() {
        return id;
    }

    public String worldId() {
        return worldId;
    }

    public boolean enabled() {
        return enabled;
    }

    public double health() {
        return health;
    }

    public List<String> groups() {
        return groups;
    }
}