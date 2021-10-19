package util.model;

import io.intino.gamification.events.MissionEventListener;
import io.intino.gamification.graph.model.Mission;
import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.graph.model.Player;
import util.events.FixAsset;

public class FixOneAsset extends Mission {

    private static final String ID = "FixOneAsset";
    private static final String DESCRIPTION = "Arregla un proyector";
    private static final int STEPS_TO_COMPLETE = 2;
    private static final int PRIORITY = 0;

    public FixOneAsset() {
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
        missionAssignment.playerState().addScore(100);
    }

    @Override
    protected void onMissionFail(MissionAssignment missionAssignment) {
        missionAssignment.playerState().addScore(-50);
    }

    @Override
    protected void onMissionIncomplete(MissionAssignment missionAssignment) {
        float progress = missionAssignment.progress().get();
        if(progress == 0) {
            missionAssignment.playerState().addScore(-25);
        } else {
            missionAssignment.playerState().addScore(Math.round(100 * progress));
        }
    }
}
