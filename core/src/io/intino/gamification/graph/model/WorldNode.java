package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;

import java.util.Objects;

class WorldNode extends Node {

    private final String worldId;

    public WorldNode(String worldId, String id) {
        super(id);
        if(worldId == null) throw new NullPointerException("World cannot be null");
        //if(!GamificationGraph.get().worlds().exists(worldId)) throw new NoSuchElementException("World " + worldId + " does not exists");
        this.worldId = worldId;
    }

    public final String worldId() {
        return this.worldId;
    }

    public final World world() {
        return GamificationGraph.get().worlds().get(worldId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WorldNode other = (WorldNode) o;
        return Objects.equals(worldId, other.worldId) && id().equals(other.id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(world(), id());
    }
}
