package io.tetrabot.graph.model;

public final class Player extends Entity {

    public Player(String id) {
        super(id);
    }

    @Override
    public Player copy() {
        return new Player(id());
    }

    public PlayerState state() {
        return competition().currentSeason().playerStates().find(id());
    }

    public void assignMission(MissionAssignment assignment) {
        state().assignMission(assignment);
    }

    public void updateMission(MissionAssignment assignment) {
        state().updateMission(assignment);
    }

    public void completeMission(MissionAssignment assignment) {
        state().completeMission(assignment);
    }

    public void failMission(MissionAssignment assignment) {
        state().failMission(assignment);
    }
}
