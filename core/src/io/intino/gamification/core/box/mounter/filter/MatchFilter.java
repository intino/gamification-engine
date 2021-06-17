package io.intino.gamification.core.box.mounter.filter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.match.EndMatch;
import io.intino.gamification.core.graph.Match;
import io.intino.gamification.core.graph.World;

public class MatchFilter extends Filter {

    private final World world;
    private final Match match;

    public MatchFilter(CoreBox box, BeginMatch event) {
        super(box);
        this.world = box.graph().world(event.world());
        this.match = box.graph().match(event.id());
    }

    public MatchFilter(CoreBox box, EndMatch event) {
        super(box);
        this.world = box.graph().world(event.world());
        this.match = box.graph().match(event.id());
    }

    public World world() {
        return world;
    }

    public Match match() {
        return match;
    }

    public boolean beginMatchCanMount() {
        return world != null && match == null && world.match() == null;
    }

    public boolean endMatchCanMount() {
        return world != null && match != null && world.match().id().equals(match.id());
    }
}
