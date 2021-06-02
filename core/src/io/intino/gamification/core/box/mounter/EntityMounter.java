package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.graph.Entity;

import static io.intino.gamification.core.graph.Entity.MAX_HEALTH;
import static io.intino.gamification.core.graph.Entity.MIN_HEALTH;

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

        String oldValue = entity.get(event.attribute());
        String newValue = applyAction(entity, event.toMessage().type(), event.value());
        newValue = Entity.getAttributeListener(event.attribute()).onAttributeChange(entity, oldValue, newValue);

        entity.set(event.attribute(), newValue);
        entity.save$();
    }

    private String applyAction(Entity entity, String type, String value) {
        return (type.equals("Attack") || type.equals("Heal"))
                ? String.valueOf(clamp(entity.health() + Double.parseDouble(value), MIN_HEALTH, MAX_HEALTH))
                : value;
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

    private double clamp(double value, double min, double max) {
        return Math.min(Math.max(min, value), max);
    }
}
