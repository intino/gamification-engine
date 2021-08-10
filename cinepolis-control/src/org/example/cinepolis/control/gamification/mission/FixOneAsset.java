package org.example.cinepolis.control.gamification.mission;

import io.intino.gamification.events.MissionEventListener;
import io.intino.gamification.graph.model.Mission;
import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.graph.model.Player;
import org.example.cinepolis.control.gamification.events.FixAsset;

public class FixOneAsset extends Mission {

    private static final String ID = "FixOneAsset";
    private static final String DESCRIPTION = "Arregla un proyector";
    private static final int STEPS_TO_COMPLETE = 1;
    private static final int PRIORITY = 0;
    private static final long EXPIRATION_TIME_SECONDS = 600;

    public FixOneAsset() {
        super(ID, DESCRIPTION, STEPS_TO_COMPLETE, PRIORITY, EXPIRATION_TIME_SECONDS);
    }

    @Override
    protected void setProgressCallbacks() {
        subscribe(FixAsset.class, new MissionEventListener<FixAsset>() {
            @Override
            public void invoke(FixAsset event, Mission mission, Player player, MissionAssignment missionAssignment) {
                missionAssignment.progress().increment();
            }
        });
    }

    @Override
    protected void onMissionComplete(MissionAssignment missionAssignment) {

    }

    @Override
    protected void onMissionFail(MissionAssignment missionAssignment) {

    }
}
