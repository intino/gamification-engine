package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.DestroyEntity;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.world.CreateWorld;
import io.intino.gamification.core.box.events.world.DestroyWorld;
import io.intino.gamification.core.graph.Entity;
import io.intino.gamification.core.graph.World;
import io.intino.magritte.framework.Layer;

import java.time.Instant;

public class WorldMounter extends Mounter {

    public WorldMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void handle(GamificationEvent event) {
        if(event instanceof CreateWorld) handle((CreateWorld) event);
        if(event instanceof DestroyWorld) handle((DestroyWorld) event);
    }

    private void handle(CreateWorld event) {
        if(!box.graph().existWorld(event.id())) box.graph().world(event).save$();
    }

    private void handle(DestroyWorld event) {
        World world = box.graph().world(event.id());
        if(world != null) {
            world.entities().forEach(e -> box.engineTerminal().feed(destroyEntityEvent(e)));

            box.graph().matchesIn(world).forEach(m -> {
                m.missions().forEach(Layer::delete$);

                m.entitiesState().forEach(es -> {
                    es.missionState().forEach(Layer::delete$);
                    es.delete$();
                });

                m.delete$();
            });

            world.delete$();
        }
    }

    private DestroyEntity destroyEntityEvent(Entity entity) {
        return new DestroyEntity().ts(Instant.now())
                .id(entity.id());
    }
}
