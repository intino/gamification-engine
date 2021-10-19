package io.intino.gamification.graph.model;

import io.intino.gamification.events.EventManager;
import io.intino.gamification.events.MissionEventListener;
import io.intino.gamification.events.MissionProgressEvent;
import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.data.Progress;

import static io.intino.gamification.util.data.Progress.State.*;

public abstract class Mission extends Node implements Comparable<Mission> {

    private String description;
    private int priority;

    public Mission(String id) {
        super(id);
    }

    public Mission(String id, String description) {
        this(id, description, 0);
    }

    public Mission(String id, String description, int priority) {
        super(id);
        this.description = description;
        this.priority = priority;
    }

    public Mission description(String description) {
        this.description = description;
        return this;
    }

    public Mission priority(int priority) {
        this.priority = priority;
        return this;
    }

    protected final <T extends MissionProgressEvent> void subscribe(Class<T> eventType, MissionEventListener<T> listener) {
        EventManager.get().addEventCallback(eventType, event -> {
            World world = GamificationGraph.get().worlds().find(event.worldId());
            if(world == null || !world.isAvailable()) return;

            Match match = world.currentMatch();
            if(match == null || !match.isAvailable()) return;

            addMissionProgressTask(listener, event, world, match);
        });
    }

    private <T extends MissionProgressEvent> void addMissionProgressTask(MissionEventListener<T> listener, T event, World world, Match match) {
        match.runMissionProgressTask(new Match.MissionProgressTask(event.playerId()) {
            @Override
            void execute() {
                invokeTask(listener, event, world);
            }
        });
    }

    private <T extends MissionProgressEvent> void invokeTask(MissionEventListener<T> listener, T event, World world) {
        Mission mission = Mission.this;
        if(!mission.isAvailable()) return;

        Player player = playerWithId(world, event.playerId());
        if(player == null || !player.isAvailable()) return;

        MissionAssignment missionAssignment = missionAssignmentOfPlayer(mission.id(), player);
        if(missionAssignment == null) return;

        Progress.State previousState = missionAssignment.progress().state();
        listener.invoke(event, mission, player, missionAssignment);
        Progress.State newState = missionAssignment.progress().state();
        if(newState != previousState && newState != InProgress) {
            missionAssignment.update(newState);
        }
    }

    private MissionAssignment missionAssignmentOfPlayer(String missionId, Player player) {
        Match.PlayerState playerState = player.world()
                .currentMatch()
                .player(player.id());
        return playerState != null ? playerState.missionAssignment(missionId) : null;
    }

    private Player playerWithId(World world, String id) {
        return world != null ? world.players().find(id) : null;
    }

    public final String description() {
        return this.description;
    }

    public final int priority() {
        return this.priority;
    }

    @Override
    public int compareTo(Mission o) {
        return Integer.compare(priority, o.priority);
    }

    @Override
    void initTransientAttributes() {
        setProgressCallbacks();
    }

    protected abstract void setProgressCallbacks();
}
