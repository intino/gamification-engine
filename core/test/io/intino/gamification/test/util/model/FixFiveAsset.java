package io.intino.gamification.test.util.model;

import io.intino.gamification.events.MissionEventListener;
import io.intino.gamification.graph.model.Mission;
import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.graph.model.Player;
import io.intino.gamification.test.util.events.FixAsset;

public class FixFiveAsset extends Mission {

    private static final String ID = "FixFiveAsset";
    private static final String DESCRIPTION = "Arregla cinco proyectores";
    private static final int STEPS_TO_COMPLETE = 5;
    private static final int PRIORITY = 0;
    public static final int COMPLETE_SCORE = 100;
    public static final int FAIL_SCORE = -50;
    public static final int DO_NOTHING_SCORE = -25;

    public FixFiveAsset() {
        super(ID, DESCRIPTION, STEPS_TO_COMPLETE, PRIORITY);
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
        missionAssignment.addPoints(COMPLETE_SCORE);
    }

    @Override
    protected void onMissionFail(MissionAssignment missionAssignment) {
        missionAssignment.addPoints(FAIL_SCORE);
    }

    @Override
    protected void onMissionIncomplete(MissionAssignment missionAssignment) {
        float progress = missionAssignment.progress().get();
        if(progress == 0) {
            missionAssignment.addPoints(DO_NOTHING_SCORE);
        } else {
            missionAssignment.addPoints(Math.round(100 * progress));
        }
    }
}
