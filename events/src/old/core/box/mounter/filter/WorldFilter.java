package old.core.box.mounter.filter;

import old.core.box.CoreBox;
import old.core.box.events.world.CreateWorld;
import old.core.box.events.world.DestroyWorld;
import old.core.graph.World;

public class WorldFilter extends Filter {

    private final World world;

    public WorldFilter(CoreBox box, CreateWorld event) {
        super(box);

        if(event.id() == null) throwMissingEventAttributeException("id");

        this.world = box.graph().world(event.id());

        canMount(world == null);
    }

    public WorldFilter(CoreBox box, DestroyWorld event) {
        super(box);

        if(event.id() == null) throwMissingEventAttributeException("id");

        this.world = box.graph().world(event.id());

        canMount(world != null);
    }

    public World world() {
        return world;
    }
}
