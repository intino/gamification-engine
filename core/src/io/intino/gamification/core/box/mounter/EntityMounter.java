package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.box.events.attributes.MatchState;
import io.intino.gamification.core.graph.Entity;

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
        box.graph().entity(event).save$();
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

        final String oldValue = entity.get(event.attribute());
        final String newValue = event.value();

        Entity.getAttributeListener(event.attribute()).onAttributeChange(entity, oldValue, newValue);

        if(event.toMessage().type().equals("Action")) entity.set(event.attribute(), event.value());
        else if(event.toMessage().type().equals("ChangeLevel")) entity.level(Integer.parseInt(event.value()));
        else if(event.toMessage().type().equals("GainScore")) changeScore(entity, Integer.parseInt(event.value()));
        else if(event.toMessage().type().equals("LossScore")) changeScore(entity, Integer.parseInt(event.value()));
        else if(event.toMessage().type().equals("ChangeHealth")) changeHealth(entity, Integer.parseInt(event.value()));
        else if(event.toMessage().type().equals("Attack")) changeHealth(entity, entity.health() + Integer.parseInt(event.value()));
        else if(event.toMessage().type().equals("Heal")) changeHealth(entity, entity.health() + Integer.parseInt(event.value()));

        entity.save$();
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

    private void changeHealth(Entity entity, int newHealth) {
        entity.health(Math.max(0, Math.min(100, newHealth)));
    }

    private void changeScore(Entity entity, int scoreDiff) {
        entity.score(entity.score() + scoreDiff);
        box.graph().matchList(m -> m.state().equals(MatchState.Started))
                .map(m -> m.entitiesState().stream()
                        .filter(e -> e.id().equals(entity.id()))
                        .findFirst().orElse(null))
                .filter(Objects::nonNull)
                .forEach(e -> e.score(e.score() + scoreDiff));
    }
}
