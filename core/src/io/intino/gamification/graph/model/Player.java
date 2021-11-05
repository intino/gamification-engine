package io.intino.gamification.graph.model;

public class Player extends Entity {

    public Player(String id) {
        super(id);
    }

    public final void assignMission(MissionAssignment missionAssignment) {
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
        if(!competition().isAvailable()) return null;
        Season season = competition().currentSeason();
        if(season == null || !season.isAvailable()) return null;
        return season;
    }

    /*public final List<ObtainedAchievement> achievements() {
        return Collections.unmodifiableList(achievements);
    }

    public final List<ObtainedAchievement> achievementsOfType(String achievementId) {
        return achievements.stream().filter(a -> a.achievement().equals(achievementId)).collect(Collectors.toList());
    }

    public void addAchievement(String achievementId) {
        achievements.add(new ObtainedAchievement(achievementId, id(), TimeUtils.currentInstant()));
    }

    public void addAchievement(String achievementId, Instant instant) {
        achievements.add(new ObtainedAchievement(achievementId, id(), instant));
        achievements.sort(Comparator.naturalOrder());
    }

    public void removeAchievement(int index) {
        achievements.remove(index);
    }



    */
}
