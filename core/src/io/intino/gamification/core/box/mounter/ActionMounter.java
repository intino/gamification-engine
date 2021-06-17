package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.action.*;
import io.intino.gamification.core.box.mounter.builder.ActionFilter;
import io.intino.gamification.core.box.utils.MathUtils;
import io.intino.gamification.core.graph.Entity;
import io.intino.gamification.core.graph.Match;
import io.intino.gamification.core.graph.Player;
import io.intino.gamification.core.graph.PlayerState;

public class ActionMounter extends Mounter {

    public ActionMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void mount(GamificationEvent event) {
        if(event instanceof Attack) handle((Attack) event);
        else if(event instanceof Heal) handle((Heal) event);
        else if(event instanceof SetHealth) handle((SetHealth) event);
        else if(event instanceof ChangeScore) handle((ChangeScore) event);
        else if(event instanceof EnableEntity) handle((EnableEntity) event);
        else if(event instanceof DisableEntity) handle((DisableEntity) event);
    }

    private void handle(Attack event) {
        ActionFilter filter = new ActionFilter(box, event);
        if(!filter.attackCanMount()) return;

        Entity entity = filter.entity();

        Double newHealth = box.engineConfig()
                .healthListener.get()
                .onValueChange(entity, entity.health(), entity.health() - event.damage());
        entity.health(MathUtils.clamp(newHealth, Entity.MIN_HEALTH, Entity.MAX_HEALTH));

        entity.save$();
    }

    private void handle(Heal event) {
        ActionFilter filter = new ActionFilter(box, event);
        if(!filter.healCanMount()) return;

        Entity entity = filter.entity();

        Double newHealth = box.engineConfig()
                .healthListener.get()
                .onValueChange(entity, entity.health(), entity.health() + event.healedHealth());
        entity.health(newHealth);

        entity.save$();
    }

    private void handle(SetHealth event) {
        ActionFilter filter = new ActionFilter(box, event);
        if(!filter.setHealthCanMount()) return;

        Entity entity = filter.entity();

        Double newHealth = box.engineConfig()
                .healthListener.get()
                .onValueChange(entity, entity.health(), event.health());
        entity.health(newHealth);

        entity.save$();
    }

    private void handle(ChangeScore event) {
        ActionFilter filter = new ActionFilter(box, event);
        if(!filter.shiftScoreCanMount()) return;

        Match match = filter.match();
        Player player = (Player) filter.entity();

        Integer newScore = box.engineConfig()
                .scoreListener.get()
                .onValueChange(player, player.score(), player.score() + event.change());
        player.score(newScore);

        if(match != null) {
            changeMatchRelativeScore(match, player, event.change());
        }

        player.save$();
    }

    private void handle(EnableEntity event) {
        ActionFilter filter = new ActionFilter(box, event);
        if(!filter.enableEntityCanMount()) return;

        Entity entity = filter.entity();

        Boolean newEnableState = box.engineConfig()
                .enableListener.get()
                .onValueChange(entity, entity.enabled(), true);
        entity.enabled(newEnableState);

        entity.save$();
    }

    private void handle(DisableEntity event) {
        ActionFilter filter = new ActionFilter(box, event);
        if(!filter.disableEntityCanMount()) return;

        Entity entity = filter.entity();

        Boolean newEnableState = box.engineConfig()
                .enableListener.get()
                .onValueChange(entity, entity.enabled(), false);
        entity.enabled(newEnableState);

        entity.save$();
    }

    private void changeMatchRelativeScore(Match match, Player player, int shift) {
        PlayerState playerState = box.graph().playerState(match.playersState(), player.id());
        if(playerState == null) {
            playerState = box.graph().playerState(player.id());
            match.playersState().add(playerState);
            match.save$();
        }

        playerState.score(playerState.score() + shift).save$();
    }
}