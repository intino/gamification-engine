package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.EventBuilder;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.entity.*;
import io.intino.gamification.core.box.mounter.builder.EntityFilter;
import io.intino.gamification.core.graph.*;

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
        else if(event instanceof Action) handle((Action) event);
        else if(event instanceof PickUpItem) handle((PickUpItem) event);
        else if(event instanceof DropItem) handle((DropItem) event);
        else if(event instanceof EnableEntity) handle((EnableEntity) event);
        else if(event instanceof DisableEntity) handle((DisableEntity) event);
    }

    private void handle(CreatePlayer event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.createPlayerCanMount()) return;

        World world = filter.world();
        Player player = box.graph().player(event, world.id());

        world.players().add(player);
        if(event.enabled() != null) box.engineTerminal().feed(EventBuilder.enableEntity(world.id(), player.id()));
        if(event.health() != null) box.engineTerminal().feed(EventBuilder.setHealth(world.id(), player.id(), "SetHealth", 100));

        world.save$();
        player.save$();
    }

    private void handle(CreateNpc event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.createNpcCanMount()) return;

        World world = filter.world();
        Npc npc = box.graph().npc(event, world.id());

        world.npcs().add(npc);
        if(event.enabled() != null) box.engineTerminal().feed(EventBuilder.enableEntity(world.id(), npc.id()));
        if(event.health() != null) box.engineTerminal().feed(EventBuilder.setHealth(world.id(), event.id(), "SetHealth", 100));

        world.save$();
        npc.save$();
    }

    private void handle(CreateItem event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.createItemCanMount()) return;

        World world = filter.world();
        Item item = box.graph().item(event, world.id());

        world.items().add(item);
        Player player = filter.player();
        if(player != null) {
            box.engineTerminal().feed(EventBuilder.pickUpItem(world.id(), player.id(), item.id()));
        }

        if(event.enabled() != null) box.engineTerminal().feed(EventBuilder.enableEntity(world.id(), item.id()));
        if(event.health() != null) box.engineTerminal().feed(EventBuilder.setHealth(world.id(), item.id(), "SetHealth", 100));

        world.save$();
        item.save$();
    }

    private void handle(DestroyPlayer event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.destroyPlayerCanMount()) return;

        World world = filter.world();
        Player player = filter.player();

        world.players().remove(player);

        if(event.destroyStrategy() == DestroyStrategy.Cascade) {
            player.inventory().forEach(i -> box.engineTerminal().feed(EventBuilder.destroyItem(world.id(), i.id())));
        } else {
            player.inventory().forEach(i -> i.owner(null).save$());
        }

        world.save$();
        player.delete$();
    }

    private void handle(DestroyNpc event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.destroyNpcCanMount()) return;

        World world = filter.world();
        Npc npc = filter.npc();

        world.npcs().remove(npc);

        world.save$();
        npc.delete$();
    }

    private void handle(DestroyItem event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.destroyItemCanMount()) return;

        World world = filter.world();
        Item item = filter.item();

        world.items().remove(item);

        Player player = box.graph().player(world.players(), item.owner());
        if(player != null) {
            Item itemToRemove = player.inventory().stream().filter(i -> i.id().equals(item.id())).findFirst().orElse(null);
            player.inventory().remove(itemToRemove);
            player.save$();
        }

        world.save$();
        item.delete$();
    }









    private void handle(Action event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.actionCanMount()) return;

        Match match = filter.match();
        Entity entity = filter.entity();

        String oldValue = entity.get(event.attribute());
        String newValue = applyAction(match, entity, event.toMessage().type(), event.value());
        if(newValue == null) return;
        newValue = Entity.getAttributeListener(event.attribute()).onAttributeChange(entity, oldValue, newValue);
        entity.set(event.attribute(), newValue);

        entity.save$();
    }

    private void handle(PickUpItem event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.pickUpItemCanMount()) return;

        Player player = filter.player();
        Item item = filter.item();

        player.inventory().add(item);

        player.save$();
        item.save$();
    }

    private void handle(DropItem event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.dropItemCanMount()) return;

        Player player = filter.player();
        Item item = filter.item();

        player.inventory().remove(item);

        player.save$();
        item.save$();
    }

    private void handle(EnableEntity event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.enableEntityCanMount()) return;

        Entity entity = filter.entity();

        entity.enabled(true);

        entity.save$();
    }

    private void handle(DisableEntity event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.disableEntityCanMount()) return;

        Entity entity = filter.entity();

        entity.enabled(false);

        entity.save$();
    }

    private String applyAction(Match match, Entity entity, String type, String value) {
        switch (type) {
            case "SetHealth":
                return String.valueOf(Double.parseDouble(value));
            case "Attack":
                return String.valueOf(entity.health() - Double.parseDouble(value));
            case "Heal":
                return String.valueOf(entity.health() + Double.parseDouble(value));
            case "ChangeScore":
                //TODO
                if(entity instanceof Player) {
                    return applyChangeScore(match, (Player) entity, Integer.parseInt(value));
                } else {
                    return null;
                }
            default:
                return value;
        }
    }

    private String applyChangeScore(Match match, Player entity, int value) {
        changeMatchRelativeScore(match, entity, value);
        return String.valueOf(entity.score() + value);
    }

    private void changeMatchRelativeScore(Match match, Player player, int scoreDiff) {
        PlayerState playerState = box.graph().playerState(match.playersState(), player.id());
        if(playerState == null) {
            playerState = box.graph().playerState(player.id());
            match.playersState().add(playerState);
            match.save$();
        }

        playerState.score(playerState.score() + scoreDiff).save$();
    }
}