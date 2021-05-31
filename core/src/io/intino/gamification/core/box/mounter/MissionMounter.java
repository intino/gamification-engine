package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;

public class MissionMounter extends Mounter {

    public MissionMounter(CoreBox box) {
        super(box, "Entity");
    }

    @Override
    public void handle(GamificationEvent event) {
        if(event instanceof Mission) handle((Mission) event);
        if(event instanceof MissionStatus) handle((MissionStatus) event);
    }

    protected void handle(Mission event) {

    }

    protected void handle(MissionStatus event) {

    }
}
