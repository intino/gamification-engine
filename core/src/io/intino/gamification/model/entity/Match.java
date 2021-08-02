package io.intino.gamification.model.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Match extends Entity {

    private final Instant beginning;    //Obligatorio definirlo
    private final Instant expiration;
    private final boolean reboot;       //Obligatorio definirlo
    private final List<PlayerState> playersState;
    private final List<Mission> missions;

    public Match() {
        this.beginning = null;
        this.expiration = null;
        this.reboot = false;
        this.playersState = new ArrayList<>();
        this.missions = new ArrayList<>();
    }

    public static class PlayerState {

        private final Player player;
        private final int matchScore;
        private final List<MissionState> missionsState;

        public PlayerState() {
            this.player = null;
            this.matchScore = 0;
            this.missionsState = new ArrayList<>();
        }

        public static class MissionState {

            private final Mission mission;
            private final int count;

            public MissionState() {
                this.mission = null;
                this.count = 0;
            }
        }
    }
}
