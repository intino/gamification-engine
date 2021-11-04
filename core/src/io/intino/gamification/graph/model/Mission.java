package io.intino.gamification.graph.model;

import io.intino.gamification.events.EventManager;
import io.intino.gamification.events.MissionEventListener;
import io.intino.gamification.events.MissionProgressEvent;
import io.intino.gamification.util.data.Progress;

import java.util.stream.Stream;

import static io.intino.gamification.util.data.Progress.State.InProgress;

public abstract class Mission extends CompetitionNode implements Comparable<Mission> {

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

    //TODO HACE FALTA??
    protected final <T extends MissionProgressEvent> void subscribe(Class<T> eventType, MissionEventListener<T> listener) {
        EventManager.get().addEventCallback(eventType, event -> {
            Competition competition = competition();
            if(!competition.isAvailable()) return;

            Season season = competition.currentSeason();
            if(season == null || !season.isAvailable()) return;

            addMissionProgressTask(listener, event, competition, season);
        });
    }

    //TODO: Hace falta???
    private <T extends MissionProgressEvent> void addMissionProgressTask(MissionEventListener<T> listener, T event, Competition competition, Season season) {
        season.runMissionProgressTask(new Season.MissionProgressTask(event.playerId()) {
            @Override
            void execute() {
                invokeTask(listener, event, competition);
            }
        });
    }

    //TODO: Hace falta???
    private <T extends MissionProgressEvent> void invokeTask(MissionEventListener<T> listener, T event, Competition competition) {
        Mission mission = Mission.this;
        if(!mission.isAvailable()) return;

        Player player = competition.players().find(event.playerId());
        if(player == null || !player.isAvailable()) return;

        missionAssignmentOfPlayer(mission.id(), player).forEach(missionAssignment -> {
            Progress.State previousState = missionAssignment.progress().state();
            listener.invoke(event, mission, player, missionAssignment);
            Progress.State newState = missionAssignment.progress().state();
            if(newState != previousState && newState != InProgress) {
                missionAssignment.update(newState);
            }
        });
    }

    private Stream<MissionAssignment> missionAssignmentOfPlayer(String missionId, Player player) {
        Season season = competition().currentSeason();
        if(season == null) return Stream.empty();
        PlayerState playerState = season.playerStates().find(player.id());
        if(playerState == null) return Stream.empty();
        return playerState.missionAssignments().stream().filter(m -> m.missionId().equals(missionId));
    }

    @Override
    void initTransientAttributes() {
        setProgressCallbacks();
    }

    @Override
    public int compareTo(Mission o) {
        return Integer.compare(priority, o.priority);
    }

    protected abstract void setProgressCallbacks();

    /*public Mission description(String description) {
        this.description = description;
        return this;
    }

    public Mission priority(int priority) {
        this.priority = priority;
        return this;
    }







    public final int priority() {
        return this.priority;
    }




    */
}
