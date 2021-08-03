package io.intino.gamification.test.events;

import io.intino.gamification.events.GamificationEvent;

public class ArreglarProyector extends GamificationEvent {

    private final String player;
    private final String proyector;

    public ArreglarProyector(String player, String proyector) {
        this.player = player;
        this.proyector = proyector;
    }

    public String player() {
        return this.player;
    }

    public String proyector() {
        return this.proyector;
    }
}
