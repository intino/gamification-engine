package io.intino.gamification.core.box.mounter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.box.events.enumerates.ActionOperationType;
import io.intino.gamification.core.graph.Entity;

import java.util.Map;

public class EntityMounter extends Mounter {

    public EntityMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void handle(GamificationEvent event) {
        if(event instanceof Action) handle((Action) event);
        if(event instanceof AttachEntity) handle((AttachEntity) event);
        if(event instanceof DestroyEntity) handle((DestroyEntity) event);
        if(event instanceof DetachEntity) handle((DetachEntity) event);
        if(event instanceof CreateEntity) handle((CreateEntity) event);
    }

    protected void handle(Action event) {

        Entity dest = box.graph().getEntity(event.destEntity());
        changeAttribute(dest, event.destEntityAttribute(), event.value(), event.operationType());
        dest.save$();
    }

    protected void handle(AttachEntity event) {

        Entity parent = box.graph().getEntity(event.parent());
        Entity child = box.graph().getEntity(event.child());

        if(child == null) return;
        if(child.parent() == parent) return;

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

    protected void handle(DestroyEntity event) {

        Entity entity = box.graph().getEntity(event.id());

        if(entity == null) return;

        entity.children().forEach(c -> c.parent(null).save$());
        entity.delete$();
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

    protected void handle(CreateEntity event) {
        box.graph().entity(event).save$();
    }

    private void changeAttribute(Entity entity, String key, String value, ActionOperationType type) {

        Map<String, String> attributes = new Gson().fromJson(entity.attributes(), new TypeToken<Map<String, String>>(){}.getType());

        switch (type) {
            case Add:
                break;
            case Remove:
                break;
            case Replace:
                break;
            case Sum:
                break;
            case Subtract:
                break;
        }

        attributes.put(key, value);
    }
}
