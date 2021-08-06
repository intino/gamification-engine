package io.intino.gamification.graph.model;

import io.intino.gamification.events.EventManager;
import io.intino.gamification.events.MissionEventListener;
import io.intino.gamification.events.MissionProgressEvent;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;

public class Mission extends Node implements Comparable<Mission> {

    private static final long NO_EXPIRATION_TIME = Long.MAX_VALUE;

    private final String description;
    private final int stepsToComplete;
    private final int priority;
    private final long expirationTimeSeconds;

    public Mission(String id, String description, int stepsToComplete) {
        this(id, description, stepsToComplete, 0);
    }

    public Mission(String id, String description, int stepsToComplete, int priority) {
        this(id, description, stepsToComplete, priority, NO_EXPIRATION_TIME);
    }

    public Mission(String id, String description, int stepsToComplete, int priority, long expirationTimeSeconds) {
        super(id);
        this.description = description;
        this.stepsToComplete = stepsToComplete;
        this.priority = priority;
        this.expirationTimeSeconds = expirationTimeSeconds;
    }

    public <T extends MissionProgressEvent> void subscribe(Class<T> eventType, MissionEventListener<T> listener) {
        EventManager.get().addEventCallback(eventType, event -> {
            World world = GamificationGraph.get().worlds().find(event.worldId());
            Match match = world.currentMatch();
            if(match != null) addMissionProgressTask(listener, event, world, match);
        });
    }

    private <T extends MissionProgressEvent> void addMissionProgressTask(MissionEventListener<T> listener, T event, World world, Match match) {
        match.addMissionProgressTask(new Match.MissionProgressTask(event.playerId()) {
            @Override
            void execute() {
                invokeTask(listener, event, world);
            }
        });
    }

    private <T extends MissionProgressEvent> void invokeTask(MissionEventListener<T> listener, T event, World world) {
        Mission mission = Mission.this;
        Player player = playerWithId(world, event.playerId());
        if(player != null) {
            MissionAssignment missionAssignment = missionAssigmentOfPlayer(mission.id(), player);
            if(missionAssignment != null) {
                listener.invoke(event, mission, player, missionAssignment);
            }
        }
    }

    private MissionAssignment missionAssigmentOfPlayer(String missionId, Player player) {
        return player.world()
                .currentMatch()
                .player(player.id())
                .missionAssignment(missionId);
    }

    private Player playerWithId(World world, String id) {
        return world != null ? world.players().find(id) : null;
    }

    boolean hasExpired(Instant referenceTs) {
        if(!hasExpirationTime()) return false;
        final Instant now = TimeUtils.currentInstant();
        final Instant expirationTs = referenceTs.plusSeconds(expirationTimeSeconds);
        return !now.isBefore(expirationTs);
    }

    private boolean hasExpirationTime() {
        return expirationTimeSeconds != NO_EXPIRATION_TIME;
    }

    public String description() {
        return this.description;
    }

    public int total() {
        return stepsToComplete;
    }

    public int priority() {
        return this.priority;
    }

    public long expirationTimeSeconds() {
        return this.expirationTimeSeconds;
    }

    @Override
    public int compareTo(Mission o) {
        return Integer.compare(priority, o.priority);
    }
}
