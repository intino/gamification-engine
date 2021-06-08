package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
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
        else if(event instanceof CreateEnemy) handle((CreateEnemy) event);
        else if(event instanceof CreateNpc) handle((CreateNpc) event);
        else if(event instanceof CreateItem) handle((CreateItem) event);
        else if(event instanceof DestroyEntity) handle((DestroyEntity) event);
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
        Player player = box.graph().player(event, world);

        world.entities().add(player);

        world.save$();
        player.save$();
    }

    private void handle(CreateEnemy event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.createEnemyCanMount()) return;

        World world = filter.world();
        Enemy enemy = box.graph().enemy(event, world);

        world.entities().add(enemy);

        world.save$();
        enemy.save$();
    }

    private void handle(CreateNpc event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.createNpcCanMount()) return;

        World world = filter.world();
        Npc npc = box.graph().npc(event, world);

        world.entities().add(npc);

        world.save$();
        npc.save$();
    }

    private void handle(CreateItem event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.createItemCanMount()) return;

        World world = filter.world();
        Item item = box.graph().item(event, world);

        world.entities().add(item);

        world.save$();
        item.save$();
    }

    private void handle(DestroyEntity event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.destroyEntityCanMount()) return;

        World world = filter.world();
        Entity entity = filter.entity();

        world.entities().remove(entity);
        destroyEntity(entity);

        world.save$();
        entity.delete$();
    }

    private void handle(Action event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.actionCanMount()) return;

        Entity entity = filter.entity();

        String oldValue = entity.get(event.attribute());
        String newValue = applyAction(entity, event.toMessage().type(), event.value());
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
        item.owner(player);

        player.save$();
        item.save$();
    }

    private void handle(DropItem event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.dropItemCanMount()) return;

        Player player = filter.player();
        Item item = filter.item();

        player.inventory().remove(item);
        item.owner(null);

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

        PlayerState playerState = box.graph().playerState(match.playersState(), player.id());
        if(playerState == null) {
            playerState = box.graph().playerState(player, match);
            match.playersState().add(playerState);
            match.save$();
        }

        playerState.score(playerState.score() + scoreDiff).save$();
    }
}