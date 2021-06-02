package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.graph.Mission;

public class MissionMounter extends Mounter {

    public MissionMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void handle(GamificationEvent event) {
        if(event instanceof NewMission) handle((NewMission) event);
        if(event instanceof MissionNewState) handle((MissionNewState) event);
    }

    protected void handle(NewMission event) {

        Mission mission = box.graph().getMission(event.id());

        if(mission != null) return;

        box.graph().mission(event).save$();
    }

    protected void handle(MissionNewState event) {

        Mission mission = box.graph().getMission(event.id());

        if(mission == null) return;

        mission.state(event.state())
                .save$();
    }
}
