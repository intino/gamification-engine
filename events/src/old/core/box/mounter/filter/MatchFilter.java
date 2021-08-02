package old.core.box.mounter.filter;

import old.core.box.CoreBox;
import old.core.box.events.match.BeginMatch;
import old.core.box.events.match.EndMatch;
import old.core.graph.Match;
import old.core.graph.World;

public class MatchFilter extends Filter {

    private final World world;
    private Match match;

    public MatchFilter(CoreBox box, BeginMatch event) {
        super(box);

        if(event.world() == null) throwMissingEventAttributeException("world");
        if(event.reboot() == null) throwMissingEventAttributeException("reboot");
        if(event.ts() == null) throwMissingEventAttributeException("ts");

        this.world = box.graph().world(event.world());

        canMount(world != null && world.match() == null);
    }

    public MatchFilter(CoreBox box, EndMatch event) {
        super(box);

        if(event.id() == null) throwMissingEventAttributeException("id");
        if(event.world() == null) throwMissingEventAttributeException("world");
        if(event.ts() == null) throwMissingEventAttributeException("ts");

        this.world = box.graph().world(event.world());
        this.match = box.graph().match(event.id());

        canMount(world != null && match != null && world.match().id().equals(match.id()));
    }

    public World world() {
        return world;
    }

    public Match match() {
        return match;
    }
}
