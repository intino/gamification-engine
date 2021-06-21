package io.intino.gamification.core.box.mounter.filter;

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

        if(event.world() == null) throwMissingEventAttributeException("world");
        if(event.entityDest() == null) throwMissingEventAttributeException("entityDest");
        if(event.damage() == null) throwMissingEventAttributeException("damage");
        if(event.damage() < 0) throwInvalidAttributeValueException("entityDest", String.valueOf(event.damage()), "Value must be 0 or more.");

        this.world = box.graph().world(event.world());
        if(world != null) {
            this.entity = box.graph().entity(world.entities(), event.entityDest());
        }

        canMount(entity != null);
    }

    public ActionFilter(CoreBox box, Heal event) {
        super(box);

        if(event.world() == null) throwMissingEventAttributeException("world");
        if(event.entityDest() == null) throwMissingEventAttributeException("entityDest");
        if(event.healedHealth() == null) throwMissingEventAttributeException("healedHealth");
        if(event.healedHealth() < 0) throwInvalidAttributeValueException("healedHealth", String.valueOf(event.healedHealth()), "Value must be 0 or more.");

        this.world = box.graph().world(event.world());
        if(world != null) {
            this.entity = box.graph().entity(world.entities(), event.entityDest());
        }

        canMount(entity != null);
    }

    public ActionFilter(CoreBox box, SetHealth event) {
        super(box);

        if(event.world() == null) throwMissingEventAttributeException("world");
        if(event.entityDest() == null) throwMissingEventAttributeException("entityDest");
        if(event.health() == null) throwMissingEventAttributeException("health");
        if(event.health() < Entity.MIN_HEALTH || event.health() > Entity.MAX_HEALTH)
            throwInvalidAttributeValueException("health", String.valueOf(event.health()), "Value must be between " + Entity.MIN_HEALTH + " and " + Entity.MAX_HEALTH + ".");

        this.world = box.graph().world(event.world());
        if(world != null) {
            this.entity = box.graph().entity(world.entities(), event.entityDest());
        }

        canMount(entity != null);
    }

    public ActionFilter(CoreBox box, ChangeScore event) {
        super(box);

        if(event.world() == null) throwMissingEventAttributeException("world");
        if(event.entityDest() == null) throwMissingEventAttributeException("entityDest");
        if(event.change() == null) throwMissingEventAttributeException("change");

        this.world = box.graph().world(event.world());
        if(world != null) {
            this.match = world.match();
            this.entity = box.graph().player(world.players(), event.entityDest());
        }

        canMount(entity != null);
    }

    public ActionFilter(CoreBox box, EnableEntity event) {
        super(box);

        if(event.world() == null) throwMissingEventAttributeException("world");
        if(event.entityDest() == null) throwMissingEventAttributeException("entityDest");

        this.world = box.graph().world(event.world());
        if(world != null) {
            this.entity = box.graph().entity(world.entities(), event.id());
        }

        canMount(entity != null);
    }

    public ActionFilter(CoreBox box, DisableEntity event) {
        super(box);

        if(event.world() == null) throwMissingEventAttributeException("world");
        if(event.entityDest() == null) throwMissingEventAttributeException("entityDest");

        this.world = box.graph().world(event.world());
        if(world != null) {
            this.entity = box.graph().entity(world.entities(), event.id());
        }

        canMount(entity != null);
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
}
