package io.intino.gamification.graph.model;

import io.intino.gamification.util.data.Property;

public abstract class Entity extends WorldNode {

    //TODO CONTROLAR ENTIDADES DESACTIVADAS AL TERMINAR UNA MISIÓN/PARTIDA
    private final Property<Boolean> enabled = new Property<>(true);
    private final Property<Float> health = new Property<>(100.0f);

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

    public float health() {
        return health.get();
    }

    public void health(float health) {
        this.health.set(health);
    }

    public Property<Float> healthProperty() {
        return health;
    }

    protected void onEnable() {}
    protected void onDisable() {}

    protected void onStart() {}
    protected void onDestroy() {}

    protected void onMatchBegin(Match match) {}
    protected void onMatchUpdate(Match match) {}
    protected void onMatchEnd(Match match) {}
}

