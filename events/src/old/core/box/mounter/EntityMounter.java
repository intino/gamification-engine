package old.core.box.mounter;

import old.core.box.CoreBox;
import old.core.box.events.EventBuilder;
import old.core.box.events.GamificationEvent;
import old.core.box.events.entity.*;
import old.core.model.attributes.DestroyStrategy;
import old.core.box.mounter.filter.EntityFilter;
import old.core.graph.Item;
import old.core.graph.Npc;
import old.core.graph.Player;
import old.core.graph.World;

public class EntityMounter extends Mounter {

    public EntityMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void mount(GamificationEvent event) {
        if(event instanceof CreatePlayer) handle((CreatePlayer) event);
        else if(event instanceof CreateNpc) handle((CreateNpc) event);
        else if(event instanceof CreateItem) handle((CreateItem) event);
        else if(event instanceof DestroyPlayer) handle((DestroyPlayer) event);
        else if(event instanceof DestroyNpc) handle((DestroyNpc) event);
        else if(event instanceof DestroyItem) handle((DestroyItem) event);
        else if(event instanceof PickUpItem) handle((PickUpItem) event);
        else if(event instanceof DropItem) handle((DropItem) event);
    }

    private void handle(CreatePlayer event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.canMount()) return;

        World world = filter.world();
        Player player = box.graph().player(event, world.id());

        world.players().add(player);
        if(event.enabled() != null) player.enabled(event.enabled());
        if(event.health() != null) player.health(event.health());

        world.save$();
        player.save$();
    }

    private void handle(CreateNpc event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.canMount()) return;

        World world = filter.world();
        Npc npc = box.graph().npc(event, world.id());

        world.npcs().add(npc);
        if(event.enabled() != null) npc.enabled(event.enabled());
        if(event.health() != null) npc.health(event.health());

        world.save$();
        npc.save$();
    }

    private void handle(CreateItem event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.canMount()) return;

        World world = filter.world();
        Item item = box.graph().item(event, world.id());

        world.items().add(item);
        Player player = filter.player();
        if(player != null) {
            box.terminal().feed(EventBuilder.pickUpItem(world.id(), item.id(), player.id()));
        }

        if(event.enabled() != null) item.enabled(event.enabled());
        if(event.health() != null) item.health(event.health());

        world.save$();
        item.save$();
    }

    private void handle(DestroyPlayer event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.canMount()) return;

        World world = filter.world();
        Player player = filter.player();

        world.players().remove(player);

        if(event.destroyStrategy() == DestroyStrategy.Cascade) {
            player.inventory().forEach(i -> box.terminal().feed(EventBuilder.destroyItem(world.id(), i.id())));
        } else {
            player.inventory().forEach(i -> i.owner(null).save$());
        }

        world.save$();
        player.delete$();
    }

    private void handle(DestroyNpc event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.canMount()) return;

        World world = filter.world();
        Npc npc = filter.npc();

        world.npcs().remove(npc);

        world.save$();
        npc.delete$();
    }

    private void handle(DestroyItem event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.canMount()) return;

        World world = filter.world();
        Item item = filter.item();

        world.items().remove(item);

        Player player = box.graph().player(world.players(), item.owner());
        if(player != null) {
            Item itemToRemove = box.graph().item(player.inventory(), item.id());
            player.inventory().remove(itemToRemove);
            player.save$();
        }

        world.save$();
        item.delete$();
    }

    private void handle(PickUpItem event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.canMount()) return;

        Player player = filter.player();
        Item item = filter.item();

        player.inventory().add(item);
        item.owner(player.id());

        player.save$();
        item.save$();
    }

    private void handle(DropItem event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.canMount()) return;

        Player player = filter.player();
        Item item = filter.item();

        player.inventory().remove(item);
        item.owner(null);

        player.save$();
        item.save$();
    }
}