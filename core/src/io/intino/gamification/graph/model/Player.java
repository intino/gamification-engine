package io.intino.gamification.graph.model;

public class Player extends Entity {

    public Player(String id) {
        super(id);
    }

    public final void assignMission(MissionAssignment missionAssignment) {
        if(missionAssignment == null) throw new NullPointerException("MissionAssignment cannot be null");
        Season season = availableSeason();
        if(season != null) {
            season.playerStates().find(id()).assignMission(missionAssignment);
        }
    }

    public final void failMission(String missionId) {
        Season season = availableSeason();
        if(season != null) {
            season.playerStates().find(id()).failMission(missionId);
        }
    }

    public final void completeMission(String missionId) {
        Season season = availableSeason();
        if(season != null) {
            season.playerStates().find(id()).completeMission(missionId);
        }
    }

    public final void cancelMission(String missionId) {
        Season season = availableSeason();
        if(season != null) {
            season.playerStates().find(id()).cancelMission(missionId);
        }
    }

    private Season availableSeason() {
        if(!parent().isAvailable()) return null;
        Season season = parent().currentSeason();
        if(season == null || !season.isAvailable()) return null;
        return season;
    }
}
