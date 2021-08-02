package io.intino.gamification.model.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Mission {

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
}
