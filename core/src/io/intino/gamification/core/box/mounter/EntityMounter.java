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
        Player player = box.graph().player(event, world.id());

        world.entities().add(player);
        if(event.enabled() != null) player.enabled(event.enabled());
        if(event.health() != null) player.health(event.health());

        world.save$();
        player.save$();
    }

    private void handle(CreateEnemy event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.createEnemyCanMount()) return;

        World world = filter.world();
        Enemy enemy = box.graph().enemy(event, world.id());

        world.entities().add(enemy);
        if(event.enabled() != null) enemy.enabled(event.enabled());
        if(event.health() != null) enemy.health(event.health());

        world.save$();
        enemy.save$();
    }

    private void handle(CreateNpc event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.createNpcCanMount()) return;

        World world = filter.world();
        Npc npc = box.graph().npc(event, world.id());

        world.entities().add(npc);
        if(event.enabled() != null) npc.enabled(event.enabled());
        if(event.health() != null) npc.health(event.health());

        world.save$();
        npc.save$();
    }

    private void handle(CreateItem event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.createItemCanMount()) return;

        World world = filter.world();
        Item item = box.graph().item(event, world.id());

        world.entities().add(item);
        if(event.enabled() != null) item.enabled(event.enabled());
        if(event.health() != null) item.health(event.health());

        world.save$();
        item.save$();
    }

    private void handle(DestroyEntity event) {
        EntityFilter filter = new EntityFilter(box, event);
        if(!filter.destroyEntityCanMount()) return;

        World world = filter.world();
        Entity entity = filter.entity();

        world.entities().remove(entity);
        destroyEntity(world, entity);

        world.save$();
        entity.delete$();
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

    private void destroyEntity(World world, Entity entity) {
        //TODO
        if(entity instanceof Item) {
            world.players().forEach(p -> {
                if(p.inventory().stream().anyMatch(i -> i.id().equals(entity.id()))) {
                    Item itemToRemove = p.inventory().stream().filter(i -> i.id().equals(entity.id())).findFirst().orElse(null);
                    p.inventory().remove(itemToRemove);
                    p.save$();
                }
            });
        }
    }

    private String applyAction(Match match, Entity entity, String type, String value) {
        switch (type) {
            case "Attack":
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