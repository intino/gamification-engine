package old.core.box.mounter;

import old.core.box.CoreBox;
import old.core.box.events.EventBuilder;
import old.core.box.events.GamificationEvent;
import old.core.box.events.world.CreateWorld;
import old.core.box.events.world.DestroyWorld;
import old.core.box.mounter.filter.WorldFilter;
import old.core.graph.World;
import io.intino.magritte.framework.Layer;

public class WorldMounter extends Mounter {

    public WorldMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void mount(GamificationEvent event) {
        if(event instanceof CreateWorld) handle((CreateWorld) event);
        else if(event instanceof DestroyWorld) handle((DestroyWorld) event);
    }

    private void handle(CreateWorld event) {
        WorldFilter filter = new WorldFilter(box, event);
        if(!filter.canMount()) return;
        box.graph().world(event).save$();
    }

    private void handle(DestroyWorld event) {
        WorldFilter filter = new WorldFilter(box, event);
        if(!filter.canMount()) return;

        World world = filter.world();

        world.achievements().forEach(a -> box.terminal().feed(EventBuilder.deleteAchievement(a.id())));
        world.players().forEach(e -> box.terminal().feed(EventBuilder.destroyPlayer(world.id(), e.id())));
        world.npcs().forEach(e -> box.terminal().feed(EventBuilder.destroyNpc(world.id(), e.id())));
        world.items().forEach(e -> box.terminal().feed(EventBuilder.destroyItem(world.id(), e.id())));

        box.graph().matchesIn(world).forEach(ma -> {
            ma.missions().forEach(mi -> {
                box.graph().missionState(mi.id()).forEach(Layer::delete$);
                mi.delete$();
            });

            ma.achievements().forEach(a -> box.terminal().feed(EventBuilder.deleteAchievement(a.id())));

            ma.playersState().forEach(ps -> {
                ps.missionState().forEach(Layer::delete$);
                ps.achievements().forEach(Layer::delete$);
                ps.delete$();
            });

            ma.delete$();
        });

        world.delete$();
    }
}
