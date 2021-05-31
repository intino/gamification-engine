package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;

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
        if(event instanceof Entity) handle((Entity) event);
    }

    protected void handle(Action event) {

    }

    protected void handle(AttachEntity event) {

    }

    protected void handle(DestroyEntity event) {

    }

    protected void handle(DetachEntity event) {

    }

    protected void handle(Entity event) {

    }
}
