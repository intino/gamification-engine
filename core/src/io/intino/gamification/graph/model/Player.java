package io.intino.gamification.graph.model;

public final class Player extends Entity {

    public Player(String id) {
        super(id);
    }

    public void assignMission(MissionAssignment assignment) {
        PlayerState playerState = competition().currentSeason().playerStates().find(id());
        playerState.assignMission(assignment);
    }

    public void updateMission(MissionAssignment assignment) {
        PlayerState playerState = competition().currentSeason().playerStates().find(id());
        playerState.updateMission(assignment);
    }

    public void completeMission(MissionAssignment assignment) {
        PlayerState playerState = competition().currentSeason().playerStates().find(id());
        playerState.completeMission(assignment);
    }

    public void failMission(MissionAssignment assignment) {
        PlayerState playerState = competition().currentSeason().playerStates().find(id());
        playerState.failMission(assignment);
    }
}
