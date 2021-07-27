package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.achievement.AchievementNewState;
import io.intino.gamification.core.box.events.achievement.CreateAchievement;
import io.intino.gamification.core.box.events.achievement.DeleteAchievement;
import io.intino.gamification.core.box.events.action.*;
import io.intino.gamification.core.box.events.entity.*;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.match.EndMatch;
import io.intino.gamification.core.box.events.mission.CreateMission;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.box.events.world.CreateWorld;
import io.intino.gamification.core.box.events.world.DestroyWorld;
import io.intino.gamification.core.box.mounter.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Terminal {

    private final CoreBox box;

    public Terminal(CoreBox box) {
        this.box = box;
    }

    public void feed(AchievementNewState event) {
        box.mounter(AchievementMounter.class).handle(event);
    }

    public void feed(CreateAchievement event) {
        box.mounter(AchievementMounter.class).handle(event);
    }

    public void feed(DeleteAchievement event) {
        box.mounter(AchievementMounter.class).handle(event);
    }

    public void feed(Action event) {
        box.mounter(ActionMounter.class).handle(event);
    }

    public void feed(Attack event) {
        box.mounter(ActionMounter.class).handle(event);
    }

    public void feed(DisableEntity event) {
        box.mounter(ActionMounter.class).handle(event);
    }

    public void feed(EnableEntity event) {
        box.mounter(ActionMounter.class).handle(event);
    }

    public void feed(Heal event) {
        box.mounter(ActionMounter.class).handle(event);
    }

    public void feed(SetHealth event) {
        box.mounter(ActionMounter.class).handle(event);
    }

    public void feed(ChangeScore event) {
        box.mounter(ActionMounter.class).handle(event);
    }

    public void feed(CreatePlayer event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(CreateNpc event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(CreateItem event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(DestroyPlayer event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(DestroyNpc event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(DestroyItem event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(PickUpItem event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(DropItem event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(BeginMatch event) {
        box.mounter(MatchMounter.class).handle(event);
    }

    public void feed(EndMatch event) {
        box.mounter(MatchMounter.class).handle(event);
    }

    public void feed(CreateMission event) {
        box.mounter(MissionMounter.class).handle(event);
    }

    public void feed(NewStateMission event) {
        box.mounter(MissionMounter.class).handle(event);
    }

    public void feed(CreateWorld event) {
        box.mounter(WorldMounter.class).handle(event);
    }

    public void feed(DestroyWorld event) {
        box.mounter(WorldMounter.class).handle(event);
    }

    public <T extends GamificationEvent> void feed(T event) {
        this.<T>feedFunctionOf(event.getClass()).accept(this, event);
    }

    @SuppressWarnings("unchecked")
    private <T extends GamificationEvent> BiConsumer<Terminal, T> feedFunctionOf(Class<? extends GamificationEvent> eventClass) {
        return (BiConsumer<Terminal, T>) FeedFunctions.getOrDefault(eventClass, this::doNothing);
    }

    private void doNothing(Terminal Terminal, GamificationEvent event) {
    }

    private static final Map<Class<? extends GamificationEvent>, BiConsumer<Terminal, ? extends GamificationEvent>> FeedFunctions = new HashMap<>(){
        {
            put(CreateAchievement.class, (BiConsumer<Terminal, CreateAchievement>)Terminal::feed);
            put(DeleteAchievement.class, (BiConsumer<Terminal, DeleteAchievement>)Terminal::feed);
            put(Action.class, (BiConsumer<Terminal, Attack>)Terminal::feed);
            put(Attack.class, (BiConsumer<Terminal, Attack>)Terminal::feed);
            put(DisableEntity.class, (BiConsumer<Terminal, DisableEntity>)Terminal::feed);
            put(EnableEntity.class, (BiConsumer<Terminal, EnableEntity>)Terminal::feed);
            put(Heal.class, (BiConsumer<Terminal, Heal>)Terminal::feed);
            put(SetHealth.class, (BiConsumer<Terminal, SetHealth>)Terminal::feed);
            put(CreatePlayer.class, (BiConsumer<Terminal, CreatePlayer>)Terminal::feed);
            put(CreateNpc.class, (BiConsumer<Terminal, CreateNpc>)Terminal::feed);
            put(CreateItem.class, (BiConsumer<Terminal, CreateItem>)Terminal::feed);
            put(DestroyPlayer.class, (BiConsumer<Terminal, DestroyPlayer>)Terminal::feed);
            put(DestroyNpc.class, (BiConsumer<Terminal, DestroyNpc>)Terminal::feed);
            put(DestroyItem.class, (BiConsumer<Terminal, DestroyItem>)Terminal::feed);
            put(PickUpItem.class, (BiConsumer<Terminal, PickUpItem>)Terminal::feed);
            put(DropItem.class, (BiConsumer<Terminal, DropItem>)Terminal::feed);
            put(BeginMatch.class, (BiConsumer<Terminal, BeginMatch>)Terminal::feed);
            put(EndMatch.class, (BiConsumer<Terminal, EndMatch>)Terminal::feed);
            put(CreateMission.class, (BiConsumer<Terminal, CreateMission>)Terminal::feed);
            put(CreateWorld.class, (BiConsumer<Terminal, CreateWorld>)Terminal::feed);
            put(DestroyWorld.class, (BiConsumer<Terminal, DestroyWorld>)Terminal::feed);
        }};
}
