package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.box.events.attributes.MatchState;
import io.intino.gamification.core.graph.Match;
import io.intino.gamification.core.graph.World;

public class MatchMounter extends Mounter {

    public MatchMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void handle(GamificationEvent event) {
        if(event instanceof MatchBegin) handle((MatchBegin) event);
        if(event instanceof MatchEnd) handle((MatchEnd) event);
    }

    protected void handle(MatchBegin event) {

        World world = box.graph().getWorld(event.world());

        if(world == null) return;

        Match match = world.match();

        if(match != null) return;

        match = box.graph().match(event);
        world.match(match);

        match.save$();
        world.save$();
    }

    protected void handle(MatchEnd event) {

        Match match = box.graph().getMatch(event.id());

        if(match == null) return;

        match.to(event.ts()).state(MatchState.Finished).save$();

        World world = box.graph().getWorld(match);
        if(world != null) world.match(null).save$();
    }
}
