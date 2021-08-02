package old.core.box.mounter;

import old.core.box.CoreBox;
import old.core.box.events.GamificationEvent;
import old.core.box.events.match.BeginMatch;
import old.core.box.events.match.EndMatch;
import old.core.box.helper.MissionHelper;
import old.core.box.mounter.filter.MatchFilter;
import old.core.graph.Match;
import old.core.graph.Mission;
import old.core.graph.World;

import static old.core.model.attributes.MatchState.Finished;

public class MatchMounter extends Mounter {

    public MatchMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void mount(GamificationEvent event) {
        if(event instanceof BeginMatch) handle((BeginMatch) event);
        else if(event instanceof EndMatch) handle((EndMatch) event);
    }

    private void handle(BeginMatch event) {
        MatchFilter filter = new MatchFilter(box, event);
        if(!filter.canMount()) return;

        World world = filter.world();
        Match match = box.graph().match(event, world.id());

        world.match(match);
        if(event.expiration() != null) match.to(event.expiration());

        world.save$();
        match.save$();
    }

    private void handle(EndMatch event) {
        MatchFilter filter = new MatchFilter(box, event);
        if(!filter.canMount()) return;

        World world = filter.world();
        Match match = filter.match();

        box.helper(MissionHelper.class).failMissions(world, Mission::isActive);
        world.match(null);
        match.to(event.ts()).state(Finished);

        world.save$();
        match.save$();
    }
}