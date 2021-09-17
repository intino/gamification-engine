package org.example.cinepolis.control.gamification.model;

import io.intino.gamification.events.MissionEventListener;
import io.intino.gamification.graph.model.Mission;
import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.graph.model.Player;
import io.intino.gamification.util.data.Progress;
import org.example.cinepolis.control.gamification.events.FixAsset;

public class FixOneAsset extends Mission {

    private static final String ID = "FixOneAsset";
    private static final String DESCRIPTION = "Arregla un proyector";
    private static final int STEPS_TO_COMPLETE = 1;
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
    protected void onMissionEnd(MissionAssignment assignment) {
        int deltaScore = Math.round(scoreOfStatus(assignment) + scoreOfProgress(assignment) + scoreOfFail(assignment));
        assignment.addPoints(deltaScore);
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
