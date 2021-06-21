package io.intino.gamification.core.box.mounter.filter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.entity.*;
import io.intino.gamification.core.graph.*;

public class EntityFilter extends Filter {

    private final World world;
    private Player player;
    private Npc npc;
    private Item item;

    public EntityFilter(CoreBox box, CreatePlayer event) {
        super(box);

        if(event.id() == null) throwMissingEventAttributeException("id");
        if(event.world() == null) throwMissingEventAttributeException("world");

        this.world = box.graph().world(event.world());
        this.player = box.graph().player(event.id());

        canMount(player == null && world != null);
    }

    public EntityFilter(CoreBox box, CreateNpc event) {
        super(box);

        if(event.id() == null) throwMissingEventAttributeException("id");
        if(event.world() == null) throwMissingEventAttributeException("world");

        this.world = box.graph().world(event.world());
        this.npc = box.graph().npc(event.id());

        canMount(npc == null && world != null);
    }

    public EntityFilter(CoreBox box, CreateItem event) {
        super(box);

        if(event.id() == null) throwMissingEventAttributeException("id");
        if(event.world() == null) throwMissingEventAttributeException("world");

        this.world = box.graph().world(event.world());
        this.item = box.graph().item(event.id());
        if(world != null) {
            this.player = box.graph().player(world.players(), event.player());
        }

        canMount(item == null && world != null);
    }

    public EntityFilter(CoreBox box, DestroyPlayer event) {
        super(box);

        if(event.id() == null) throwMissingEventAttributeException("id");
        if(event.world() == null) throwMissingEventAttributeException("world");
        if(event.destroyStrategy() == null) throwMissingEventAttributeException("destroyStrategy");

        this.world = box.graph().world(event.world());
        if(world != null) {
            this.player = box.graph().player(world.players(), event.id());
        }

        canMount(player != null && world != null);
    }

    public EntityFilter(CoreBox box, DestroyNpc event) {
        super(box);

        if(event.id() == null) throwMissingEventAttributeException("id");
        if(event.world() == null) throwMissingEventAttributeException("world");

        this.world = box.graph().world(event.world());
        if(world != null) {
            this.npc = box.graph().npc(world.npcs(), event.id());
        }

        canMount(npc != null && world != null);
    }

    public EntityFilter(CoreBox box, DestroyItem event) {
        super(box);

        if(event.id() == null) throwMissingEventAttributeException("id");
        if(event.world() == null) throwMissingEventAttributeException("world");

        this.world = box.graph().world(event.world());
        if(world != null) {
            this.item = box.graph().item(world.items(), event.id());
        }

        canMount(item != null && world != null);
    }

    public EntityFilter(CoreBox box, PickUpItem event) {
        super(box);

        if(event.id() == null) throwMissingEventAttributeException("id");
        if(event.world() == null) throwMissingEventAttributeException("world");
        if(event.player() == null) throwMissingEventAttributeException("player");

        this.world = box.graph().world(event.world());
        if(world != null) {
            this.item = box.graph().item(world.items(), event.id());
            this.player = box.graph().player(world.players(), event.player());
        }

        canMount(player != null && item != null);
    }

    public EntityFilter(CoreBox box, DropItem event) {
        super(box);

        if(event.id() == null) throwMissingEventAttributeException("id");
        if(event.world() == null) throwMissingEventAttributeException("world");
        if(event.player() == null) throwMissingEventAttributeException("player");

        this.world = box.graph().world(event.world());
        if(world != null) {
            this.item = box.graph().item(world.items(), event.id());
            this.player = box.graph().player(world.players(), event.player());
        }

        canMount(player != null && item != null);
    }

    public World world() {
        return world;
    }

    public Player player() {
        return player;
    }

    public Npc npc() {
        return npc;
    }

    public Item item() {
        return item;
    }
}
