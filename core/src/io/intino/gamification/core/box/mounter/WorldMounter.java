package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.achievement.DeleteAchievement;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.entity.DestroyEntity;
import io.intino.gamification.core.box.events.world.CreateWorld;
import io.intino.gamification.core.box.events.world.DestroyWorld;
import io.intino.gamification.core.graph.Achievement;
import io.intino.gamification.core.graph.Entity;
import io.intino.gamification.core.graph.World;
import io.intino.magritte.framework.Layer;

import java.time.Instant;

public class WorldMounter extends Mounter {

    public WorldMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void mount(GamificationEvent event) {
        if(event instanceof CreateWorld) handle((CreateWorld) event);
        if(event instanceof DestroyWorld) handle((DestroyWorld) event);
    }

    private void handle(CreateWorld event) {
        World world = box.graph().world(event.id());
        if(world != null) return;
        box.graph().world(event).save$();
    }

    private void handle(DestroyWorld event) {
        World world = box.graph().world(event.id());
        if(world == null) return;

        world.achievements().forEach(a -> box.engineTerminal().feed(deleteAchievement(a)));
        world.entities().forEach(e -> box.engineTerminal().feed(destroyEntityEvent(e)));

        box.graph().matchesIn(world).forEach(ma -> {
            ma.missions().forEach(mi -> {
                box.graph().missionState(mi.id()).forEach(Layer::delete$);
                mi.delete$();
            });

            ma.achievements().forEach(a -> box.engineTerminal().feed(deleteAchievement(a)));

            ma.playersState().forEach(ps -> {
                ps.missionState().forEach(Layer::delete$);
                ps.achievements().forEach(Layer::delete$);
                ps.delete$();
            });

            ma.delete$();
        });

        world.delete$();
    }

    private DeleteAchievement deleteAchievement(Achievement achievement) {
        return new DeleteAchievement().ts(Instant.now())
                .id(achievement.id());
    }

    private DestroyEntity destroyEntityEvent(Entity entity) {
        return new DestroyEntity().ts(Instant.now())
                .id(entity.id());
    }
}
