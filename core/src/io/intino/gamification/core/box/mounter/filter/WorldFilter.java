package io.intino.gamification.core.box.mounter.filter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.world.CreateWorld;
import io.intino.gamification.core.box.events.world.DestroyWorld;
import io.intino.gamification.core.graph.World;

public class WorldFilter extends Filter {

    private final World world;

    public WorldFilter(CoreBox box, CreateWorld event) {
        super(box);
        this.world = box.graph().world(event.id());
    }

    public WorldFilter(CoreBox box, DestroyWorld event) {
        super(box);
        this.world = box.graph().world(event.id());
    }

    public World world() {
        return world;
    }

    public boolean createWorldCanMount() {
        return world == null;
    }

    public boolean destroyWorldCanMount() {
        return world != null;
    }
}
