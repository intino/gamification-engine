package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.graph.Entity;
import io.intino.magritte.framework.Layer;

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

    }

    protected void handle(AttachEntity event) {

        Entity parent = box.graph().entityList(e -> e.id().equals(event.parent()))
                .findFirst().orElse(null);

        Entity child = box.graph().entityList(e -> e.id().equals(event.child()))
                .findFirst().orElse(null);

        if(parent != null && child != null) {

            if(child.parent() != null) {

                Entity childParent = box.graph().entityList(e -> e.id().equals(child.parent().id()))
                        .findFirst().orElse(null);

                if(childParent != null) {
                    childParent.childs().remove(child);
                    childParent.save$();
                }
            }

            parent.childs().add(child);
            child.parent(parent);

            parent.save$();
            child.save$();
        }
    }

    protected void handle(DestroyEntity event) {
        box.graph().entityList(e -> e.id().equals(event.id()))
                .findFirst().ifPresent(Layer::delete$);
    }

    protected void handle(DetachEntity event) {

    }

    protected void handle(CreateEntity event) {
        box.graph().entity(event).save$();
    }
}
