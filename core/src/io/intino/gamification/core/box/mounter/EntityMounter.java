package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.box.events.attributes.MatchState;
import io.intino.gamification.core.graph.Entity;
import io.intino.gamification.core.graph.EntityState;
import io.intino.gamification.core.graph.Match;
import io.intino.gamification.core.graph.World;

import java.util.Objects;

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

    protected void handle(CreateEntity event) {
        World world = box.graph().getWorld(event.world());
        if(world == null) return;
        box.graph().entity(event, world).save$();
    }

    protected void handle(DestroyEntity event) {

        Entity entity = box.graph().getEntity(event.id());
        if(entity == null) return;
        entity.children().forEach(c -> c.parent(null).save$());
        entity.delete$();
    }

    protected void handle(Action event) {

        Entity entity = box.graph().getEntity(event.entity());

        if(entity == null) return;

        String oldValue = entity.get(event.attribute());
        String newValue = applyAction(entity, event.toMessage().type(), event.value());
        newValue = Entity.getAttributeListener(event.attribute()).onAttributeChange(entity, oldValue, newValue);

        entity.set(event.attribute(), newValue);
        entity.save$();
    }

    private String applyAction(Entity entity, String type, String value) {
        switch (type) {
            case "Attack":
            case "Heal":
                return String.valueOf(entity.health() + Double.parseDouble(value));
            case "ChangeScore":
                changeMatchRelativeScore(entity, Integer.parseInt(value));
                return String.valueOf(entity.score() + Integer.parseInt(value));
            default:
                return value;
        }
    }

    protected void handle(AttachEntity event) {

        Entity parent = box.graph().getEntity(event.parent());
        Entity child = box.graph().getEntity(event.child());

        if(child == null) return;
        for(Entity p = parent;p != null; p = p.parent()) if(p == child) return;

        if(child.parent() != null) {
            child.parent().children().remove(child);
            child.parent().save$();
        }

        child.parent(parent);
        child.save$();

        if(parent != null) {
            parent.children().add(child);
            parent.save$();
        }
    }

    protected void handle(DetachEntity event) {

        Entity parent = box.graph().getEntity(event.parent());
        Entity child = box.graph().getEntity(event.child());

        if(child == null || parent == null) return;
        if(child.parent() != parent) return;

        child.parent(null);
        child.save$();

        parent.children().remove(child);
        parent.save$();
    }

    private void changeMatchRelativeScore(Entity entity, int scoreDiff) {
        EntityState entityState = entity.world().match().entitiesState().stream()
                .filter(e -> e.id().equals(entity.id()))
                .findFirst().orElse(null);
        if(entityState == null) {
            box.graph().entityState(entity).score(scoreDiff).save$();
        } else {
            entityState.score(entityState.score() + scoreDiff).save$();
        }
    }
}
