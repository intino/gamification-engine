package io.intino.gamification.graph.model;

import io.intino.gamification.data.Property;

public abstract class Entity extends WorldNode {

    private final Property<Boolean> enabled = new Property<>(true);

    public Entity(String world, String id) {
        super(world, id);
    }

    public final Match match() {
        return world().currentMatch();
    }

    public boolean enabled() {
        return enabled.get();
    }

    public final void enable() {
        if(enabled.get()) return;
        enabled.set(true);
        onEnable();
    }

    public final void disable() {
        if(!enabled.get()) return;
        enabled.set(false);
        onDisable();
    }

    protected void onEnable() {}
    protected void onDisable() {}

    protected void onStart() {}
    protected void onDestroy() {}

    protected void onMatchBegin(Match match) {}
    protected void onMatchUpdate(Match match) {}
    protected void onMatchEnd(Match match) {}
}

