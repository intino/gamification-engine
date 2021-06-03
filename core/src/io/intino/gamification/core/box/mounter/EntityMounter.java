package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.box.events.entity.*;
import io.intino.gamification.core.graph.Entity;
import io.intino.gamification.core.graph.EntityState;
import io.intino.gamification.core.graph.Match;
import io.intino.gamification.core.graph.World;

public class EntityMounter extends Mounter {

    public EntityMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void handle(GamificationEvent event) {
        if(event instanceof CreateEntity) handle((CreateEntity) event);
        else if(event instanceof DestroyEntity) handle((DestroyEntity) event);
        else if(event instanceof Action) handle((Action) event);
        else if(event instanceof AttachEntity) handle((AttachEntity) event);
        else if(event instanceof DetachEntity) handle((DetachEntity) event);
    }

    private void handle(CreateEntity event) {
        if(box.graph().existsEntity(event.id())) return;

        World world = box.graph().world(event.world());
        if(world == null) return;

        Entity entity = box.graph().entity(event, world);
        world.entities().add(entity);

        entity.save$();
        world.save$();
    }

    private void handle(DestroyEntity event) {
        Entity entity = box.graph().entity(event.id());
        if(entity == null) return;

        World world = entity.world();
        world.entities().remove(entity);
        world.save$();

        entity.parent().children().remove(entity);
        entity.parent().save$();
        entity.children().forEach(c -> c.parent(null).save$());

        if(entity.type().equals(EntityType.Player)) {
            Match match = world.match();
            if(match != null) {
                EntityState entityState = match.entitiesState(es -> es.player().id().equals(entity.id())).stream()
                        .findFirst().orElse(null);
                if(entityState != null) {
                    match.entitiesState().remove(entityState);
                    match.save$();

                    entityState.delete$();
                }
            }
        }

        entity.delete$();
    }

    private void handle(Action event) {

        Entity entity = box.graph().entity(event.entity());

        if(entity == null) return;

        String oldValue = entity.get(event.attribute());
        String newValue = applyAction(entity, event.toMessage().type(), event.value());
        newValue = Entity.getAttributeListener(event.attribute()).onAttributeChange(entity, oldValue, newValue);

        entity.set(event.attribute(), newValue);
        entity.save$();
    }

    private void handle(AttachEntity event) {

        Entity parent = box.graph().entity(event.parent());
        Entity child = box.graph().entity(event.child());

        if(child == null || parent == null) return;
        if(child.parent().id().equals(parent.id())) return;
        for(Entity p = parent; p != null; p = p.parent()) if(p.id().equals(child.id())) return;

        if(child.parent() != null) {
            child.parent().children().remove(child);
            child.parent().save$();
        }

        child.parent(parent);
        parent.children().add(child);

        child.save$();
        parent.save$();
    }

    private void handle(DetachEntity event) {

        Entity parent = box.graph().entity(event.parent());
        Entity child = box.graph().entity(event.child());

        if(child == null || parent == null) return;
        if(child.parent() != parent) return;

        child.parent(null);
        parent.children().remove(child);

        child.save$();
        parent.save$();
    }

    private String applyAction(Entity entity, String type, String value) {
        switch (type) {
            case "Attack":
            case "Heal":
                return String.valueOf(entity.health() + Double.parseDouble(value));
            case "ChangeScore":
                if(entity.type().equals(EntityType.Player)) {
                    changeMatchRelativeScore(entity, Integer.parseInt(value));
                }
                return String.valueOf(entity.score() + Integer.parseInt(value));
            default:
                return value;
        }
    }

    private void changeMatchRelativeScore(Entity entity, int scoreDiff) {
        EntityState entityState = entity.world().match().entitiesState().stream()
                .filter(e -> e.player().id().equals(entity.id()))
                .findFirst().orElse(null);
        if(entityState == null) {
            box.graph().entityState(entity).score(scoreDiff).save$();
        } else {
            entityState.score(entityState.score() + scoreDiff).save$();
        }
    }
}
