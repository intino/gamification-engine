package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.*;
import io.intino.gamification.core.box.events.entity.*;
import io.intino.gamification.core.graph.*;

public class EntityMounter extends Mounter {

    public EntityMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void mount(GamificationEvent event) {
        if(event instanceof CreateEntity) handle((CreateEntity) event);
        else if(event instanceof DestroyEntity) handle((DestroyEntity) event);
        else if(event instanceof Action) handle((Action) event);
        else if(event instanceof PickUpItem) handle((PickUpItem) event);
        else if(event instanceof DropItem) handle((DropItem) event);
    }

    private void handle(CreateEntity event) {
        Entity entity = box.graph().entity(event.id());
        if(entity != null) return;

        World world = box.graph().world(event.world());
        if(world == null) return;

        entity = createEntity(event, world);
        world.entities().add(entity);

        entity.save$();
        world.save$();
    }

    private void handle(DestroyEntity event) {
        Entity entity = box.graph().entity(event.id());
        if(entity == null) return;

        World world = entity.world();
        world.entities().remove(entity);
        destroyEntity(entity);

        world.save$();
        entity.delete$();
    }

    private void handle(Action event) {
        Entity entity = box.graph().entity(event.entity());
        if(entity == null) return;

        String oldValue = entity.get(event.attribute());
        String newValue = applyAction(entity, event.toMessage().type(), event.value());
        if(newValue == null) return;
        newValue = Entity.getAttributeListener(event.attribute()).onAttributeChange(entity, oldValue, newValue);

        entity.set(event.attribute(), newValue);
        entity.save$();
    }

    private void handle(PickUpItem event) {

        Player player = box.graph().player(event.player());
        Item item = box.graph().item(event.item());

        if(player == null || item == null) return;
        if(item.owner() != null) return;

        player.inventory().add(item);
        item.owner(player);

        player.save$();
        item.save$();
    }

    private void handle(DropItem event) {

        Item item = box.graph().item(event.item());
        if(item == null) return;

        Player player = item.owner();
        if(player == null) return;

        item.owner(null);
        player.inventory().remove(item);

        item.save$();
        player.save$();
    }

    private Entity createEntity(CreateEntity event, World world) {
        Entity entity;
        if(event.type().equals(EntityType.Player)) entity = box.graph().player(event, world);
        else if(event.type().equals(EntityType.Enemy)) entity = box.graph().enemy(event, world);
        else if(event.type().equals(EntityType.Npc)) entity = box.graph().npc(event, world);
        else entity = box.graph().item(event, world);
        return entity;
    }

    private void destroyEntity(Entity entity) {
        //TODO
        if(entity instanceof Player) {
            ((Player) entity).inventory().forEach(i -> i.owner(null).save$());
        } else if(entity instanceof Item) {
            Item item = (Item) entity;
            item.owner().inventory().remove(item);
            item.owner().save$();
        }
    }

    private String applyAction(Entity entity, String type, String value) {
        switch (type) {
            case "Attack":
            case "Heal":
                return String.valueOf(entity.health() + Double.parseDouble(value));
            case "ChangeScore":
                //TODO
                if(entity instanceof Player) {
                    return applyChangeScore((Player) entity, Integer.parseInt(value));
                } else {
                    return null;
                }
            default:
                return value;
        }
    }

    private String applyChangeScore(Player entity, int value) {
        changeMatchRelativeScore(entity, value);
        return String.valueOf(entity.score() + value);
    }

    private void changeMatchRelativeScore(Player player, int scoreDiff) {
        Match match = player.world().match();
        if(match == null) return;

        PlayerState playerState = match.playersState(ps -> ps.player().id().equals(player.id())).stream()
                .findFirst().orElse(null);
        if(playerState == null) {
            playerState = box.graph().playerState(player, match);
            match.playersState().add(playerState);
            match.save$();
        }
        playerState.score(playerState.score() + scoreDiff).save$();
    }
}
