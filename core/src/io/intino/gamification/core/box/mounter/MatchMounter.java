package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;

public class MatchMounter extends Mounter {

    public MatchMounter(CoreBox box) {
        super(box, "Entity");
    }

    @Override
    public void handle(GamificationEvent event) {
        if(event instanceof MatchBegin) handle((MatchBegin) event);
        if(event instanceof MatchEnd) handle((MatchEnd) event);
    }

    protected void handle(MatchBegin event) {

    }

    protected void handle(MatchEnd event) {

    }
}
