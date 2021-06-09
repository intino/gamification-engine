package io.intino.gamification.core.box.mounter.builder;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.entity.*;
import io.intino.gamification.core.graph.*;

public class EntityFilter extends Filter {

    private final World world;
    private Match match;
    private Entity entity;
    private Player player;
    private Enemy enemy;
    private Npc npc;
    private Item item;

    public EntityFilter(CoreBox box, CreatePlayer event) {
        super(box);
        this.world = box.graph().world(event.world());
        this.player = box.graph().player(event.id());
    }

    public EntityFilter(CoreBox box, CreateEnemy event) {
        super(box);
        this.world = box.graph().world(event.world());
        this.enemy = box.graph().enemy(event.id());
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
    }

    public EntityFilter(CoreBox box, DestroyEntity event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.entity = box.graph().entity(world.entities(), event.id());
        }
    }

    public EntityFilter(CoreBox box, Action event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.entity = box.graph().entity(world.entities(), event.entityDest());
            this.match = world.match();
        }
    }

    public EntityFilter(CoreBox box, PickUpItem event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.item = box.graph().item(world.entities(), event.id());
            this.player = box.graph().player(world.players(), event.id());
        }
    }

    public EntityFilter(CoreBox box, DropItem event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.item = box.graph().item(world.entities(), event.id());
            this.player = box.graph().player(world.players(), event.id());
        }
    }

    public EntityFilter(CoreBox box, EnableEntity event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.entity = box.graph().entity(world.entities(), event.id());
        }
    }

    public EntityFilter(CoreBox box, DisableEntity event) {
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

    public Player player() {
        return player;
    }


    /*public Enemy enemy() {
        return enemy;
    }

    public Npc npc() {
        return npc;
    }*/
    public Item item() {
        return item;
    }

    public Match match() {
        return match;
    }

    public boolean createPlayerCanMount() {
        return player == null && world != null;
    }

    public boolean createEnemyCanMount() {
        return enemy == null && world != null;
    }

    public boolean createNpcCanMount() {
        return npc == null && world != null;
    }

    public boolean createItemCanMount() {
        return item == null && world != null;
    }

    public boolean destroyEntityCanMount() {
        return entity != null && world != null;
    }

    public boolean actionCanMount() {
        return entity != null && match != null;
    }

    public boolean pickUpItemCanMount() {
        return player != null && item != null;
    }

    public boolean dropItemCanMount() {
        return player != null && item != null;
    }

    public boolean enableEntityCanMount() {
        return entity != null;
    }

    public boolean disableEntityCanMount() {
        return entity != null;
    }
}
