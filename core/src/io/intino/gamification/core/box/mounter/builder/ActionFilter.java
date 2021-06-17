package io.intino.gamification.core.box.mounter.builder;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.action.*;
import io.intino.gamification.core.graph.Entity;
import io.intino.gamification.core.graph.Match;
import io.intino.gamification.core.graph.World;

public class ActionFilter extends Filter {

    private final World world;
    private Entity entity;
    private Match match;

    public ActionFilter(CoreBox box, Attack event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.entity = box.graph().entity(world.entities(), event.entityDest());
        }
    }

    public ActionFilter(CoreBox box, Heal event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.entity = box.graph().entity(world.entities(), event.entityDest());
        }
    }

    public ActionFilter(CoreBox box, SetHealth event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.entity = box.graph().entity(world.entities(), event.entityDest());
        }
    }

    public ActionFilter(CoreBox box, ShiftScore event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.match = world.match();
            this.entity = box.graph().player(world.players(), event.entityDest());
        }
    }

    public ActionFilter(CoreBox box, EnableEntity event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.entity = box.graph().entity(world.entities(), event.id());
        }
    }

    public ActionFilter(CoreBox box, DisableEntity event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.entity = box.graph().entity(world.entities(), event.id());
        }
    }

    public World world() {
        return world;
    }

    public Entity entity() {
        return entity;
    }

    public Match match() {
        return match;
    }

    public boolean attackCanMount() {
        return entity != null;
    }

    public boolean healCanMount() {
        return entity != null;
    }

    public boolean setHealthCanMount() {
        return entity != null;
    }

    public boolean shiftScoreCanMount() {
        return entity != null;
    }

    public boolean enableEntityCanMount() {
        return entity != null;
    }

    public boolean disableEntityCanMount() {
        return entity != null;
    }
}
