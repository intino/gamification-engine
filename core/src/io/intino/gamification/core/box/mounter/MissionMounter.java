package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.box.events.attributes.EntityType;
import io.intino.gamification.core.graph.Entity;
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

        Entity player = box.graph().getEntity(event.player());

        if(player == null) return;
        if(!player.type().equals(EntityType.Player)) return;

        Mission mission = box.graph().getMission(event.id());

        if(mission != null) return;

        box.graph().mission(event, player).save$();
    }

    protected void handle(MissionNewState event) {

        Mission mission = box.graph().getMission(event.id());

        if(mission == null) return;

        mission.state(event.state())
                .save$();
    }
}
