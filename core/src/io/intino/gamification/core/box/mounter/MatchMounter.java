package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.match.EndMatch;
import io.intino.gamification.core.box.events.match.MatchState;
import io.intino.gamification.core.graph.Match;
import io.intino.gamification.core.graph.World;

public class MatchMounter extends Mounter {

    public MatchMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void mount(GamificationEvent event) {
        if(event instanceof BeginMatch) handle((BeginMatch) event);
        if(event instanceof EndMatch) handle((EndMatch) event);
    }

    private void handle(BeginMatch event) {
        if(box.graph().existsMatch(event.id())) return;

        World world = box.graph().world(event.world());
        if(world == null) return;

        Match match = world.match();
        if(match != null) return;

        match = box.graph().match(event, world);
        world.match(match);

        match.save$();
        world.save$();
    }

    private void handle(EndMatch event) {

        Match match = box.graph().match(event.id());
        if(match == null) return;

        match.to(event.ts()).state(MatchState.Finished).save$();

        World world = match.world();
        if(world.match().id().equals(match.id())) world.match(null).save$();
    }
}
