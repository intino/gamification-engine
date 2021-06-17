package io.intino.gamification.core.box.mounter.builder;

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
        this.world = box.graph().world(event.world());
        this.player = box.graph().player(event.id());
    }

    public EntityFilter(CoreBox box, CreateNpc event) {
        super(box);
        this.world = box.graph().world(event.world());
        this.npc = box.graph().npc(event.id());
    }

    public EntityFilter(CoreBox box, CreateItem event) {
        super(box);
        this.world = box.graph().world(event.world());
        this.item = box.graph().item(event.id());
        if(world != null) {
            this.player = box.graph().player(world.players(), event.player());
        }
    }

    public EntityFilter(CoreBox box, DestroyPlayer event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.player = box.graph().player(world.players(), event.id());
        }
    }

    public EntityFilter(CoreBox box, DestroyNpc event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.npc = box.graph().npc(world.npcs(), event.id());
        }
    }

    public EntityFilter(CoreBox box, DestroyItem event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.item = box.graph().item(world.items(), event.id());
        }
    }

    public EntityFilter(CoreBox box, PickUpItem event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.item = box.graph().item(world.items(), event.id());
            this.player = box.graph().player(world.players(), event.player());
        }
    }

    public EntityFilter(CoreBox box, DropItem event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.item = box.graph().item(world.items(), event.id());
            this.player = box.graph().player(world.players(), event.player());
        }
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

    public boolean createPlayerCanMount() {
        return player == null && world != null;
    }

    public boolean createNpcCanMount() {
        return npc == null && world != null;
    }

    public boolean createItemCanMount() {
        return item == null && world != null;
    }

    public boolean destroyPlayerCanMount() {
        return player != null && world != null;
    }

    public boolean destroyNpcCanMount() {
        return npc != null && world != null;
    }

    public boolean destroyItemCanMount() {
        return item != null && world != null;
    }

    public boolean pickUpItemCanMount() {
        return player != null && item != null;
    }

    public boolean dropItemCanMount() {
        return player != null && item != null;
    }
}
