package util.model;

import io.intino.gamification.events.MissionEventListener;
import io.intino.gamification.graph.model.Mission;
import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.graph.model.Player;
import io.intino.gamification.util.data.Progress;
import util.events.FixAsset;

public class FixOneAsset extends Mission {

    private static final String ID = "FixOneAsset";
    private static final String DESCRIPTION = "Arregla un proyector";
    private static final int STEPS_TO_COMPLETE = 1;
    private static final int PRIORITY = 0;
    private static final long EXPIRATION_TIME_SECONDS = 700;

    public FixOneAsset() {
        super(ID, DESCRIPTION, STEPS_TO_COMPLETE);
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
    protected void onMissionEnd(MissionAssignment assignment) {
        //RLP
        int deltaScore = Math.round(scoreOfStatus(assignment) + scoreOfProgress(assignment) + scoreOfFail(assignment));
        assignment.assignPoints(deltaScore);
    }

    private float scoreOfStatus(MissionAssignment assignment) {
        return assignment.progress().state() == Progress.State.Complete ? 25 : 0;
    }

    private float scoreOfProgress(MissionAssignment assignment) {
        return 100 * assignment.progress().get();
    }

    private float scoreOfFail(MissionAssignment assignment) {
        return assignment.progress().get() == 0f ? -50 : 0;
    }
}
