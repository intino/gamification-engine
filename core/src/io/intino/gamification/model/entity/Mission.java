package io.intino.gamification.model.entity;

import io.intino.gamification.core.listener.SubscribedMissions;
import io.intino.gamification.events.CustomEvent;
import io.intino.gamification.model.Graph;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Mission {

    private Graph graph;

    private final String description;           //Obligatorio definirlo
    private final MissionType type;             //Obligatorio definirlo
    private final MissionDifficulty difficulty; //Obligatorio definirlo
    private final Instant expiration;
    private final int maxCount;                 //Obligatorio definirlo
    private final List<Player> players;

    public Mission() {
        this.description = null;
        this.type = null;
        this.difficulty = null;
        this.expiration = null;
        this.maxCount = 0;
        this.players = new ArrayList<>();
    }

    public void subscribe(Class<CustomEvent> customEventClass, SubscribedMissions subscribedMissions) {
        graph.core().eventManager().subscribe(customEventClass, subscribedMissions);
    }
}
