package io.intino.gamification.events;

import io.intino.gamification.graph.model.Competition;
import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.graph.model.PlayerState;
import io.intino.gamification.graph.model.Season;
import io.intino.gamification.util.Log;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;

public class MissionProgressEvent {

    private final Instant ts;
    private final MissionAssignment missionAssignment;

    MissionProgressEvent(Competition competition, String missionId, String playerId) {
        this.ts = TimeUtils.currentInstant();
        throwArgumentExceptionIfEquals(competition, null, "Competition");
        throwArgumentExceptionIfEquals(playerId, null, "PlayerId");

        Season season = competition.currentSeason();
        throwArgumentExceptionIfEquals(season, null, "CurrentSeason");

        PlayerState playerState = season.playerStates().find(playerId);
        throwArgumentExceptionIfEquals(playerState, null, "PlayerState");

        missionAssignment = playerState.missionAssignments().find(missionId);
    }

    private void throwArgumentExceptionIfEquals(Object object1, Object object2, String name) {
        if(object1 == null && object2 == null) {
            IllegalArgumentException e = new IllegalArgumentException(name + " cannot be null");
            Log.error(e);
            throw e;
        } else if(object1.equals(object2)) {
            IllegalArgumentException e = new IllegalArgumentException(name + " cannot be " + object2);
            Log.error(e);
            throw e;
        }
    }

    public final MissionAssignment missionAssignment() {
        return missionAssignment;
    }
}
