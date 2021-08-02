package old.core.box.mounter;

import old.core.box.CoreBox;
import old.core.box.events.GamificationEvent;
import old.core.box.events.action.*;
import old.core.box.mounter.filter.ActionFilter;
import old.core.box.utils.MathUtils;
import old.core.graph.Entity;
import old.core.graph.Match;
import old.core.graph.Player;
import old.core.graph.PlayerState;

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
        if(!filter.canMount()) return;

        Entity entity = filter.entity();

        Double newHealth = box.engineConfig()
                .healthListener.get()
                .onValueChange(entity, entity.health(), entity.health() - event.damage());
        entity.health(MathUtils.clamp(newHealth, Entity.MIN_HEALTH, Entity.MAX_HEALTH));

        entity.save$();
    }

    private void handle(Heal event) {
        ActionFilter filter = new ActionFilter(box, event);
        if(!filter.canMount()) return;

        Entity entity = filter.entity();

        Double newHealth = box.engineConfig()
                .healthListener.get()
                .onValueChange(entity, entity.health(), entity.health() + event.healedHealth());
        entity.health(newHealth);

        entity.save$();
    }

    private void handle(SetHealth event) {
        ActionFilter filter = new ActionFilter(box, event);
        if(!filter.canMount()) return;

        Entity entity = filter.entity();

        Double newHealth = box.engineConfig()
                .healthListener.get()
                .onValueChange(entity, entity.health(), event.health());
        entity.health(newHealth);

        entity.save$();
    }

    private void handle(ChangeScore event) {
        ActionFilter filter = new ActionFilter(box, event);
        if(!filter.canMount()) return;

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
        if(!filter.canMount()) return;

        Entity entity = filter.entity();

        Boolean newEnableState = box.engineConfig()
                .enableListener.get()
                .onValueChange(entity, entity.enabled(), true);
        entity.enabled(newEnableState);

        entity.save$();
    }

    private void handle(DisableEntity event) {
        ActionFilter filter = new ActionFilter(box, event);
        if(!filter.canMount()) return;

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