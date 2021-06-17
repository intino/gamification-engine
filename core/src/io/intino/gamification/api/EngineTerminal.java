package io.intino.gamification.api;

import io.intino.alexandria.event.Event;
import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.achievement.CreateAchievement;
import io.intino.gamification.core.box.events.achievement.DeleteAchievement;
import io.intino.gamification.core.box.events.action.*;
import io.intino.gamification.core.box.events.entity.*;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.match.EndMatch;
import io.intino.gamification.core.box.events.mission.CreateMission;
import io.intino.gamification.core.box.events.world.CreateWorld;
import io.intino.gamification.core.box.events.world.DestroyWorld;
import io.intino.gamification.core.box.mounter.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class EngineTerminal {

    private final CoreBox box;

    public EngineTerminal(CoreBox box) {
        this.box = box;
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

    public void feed(CreateWorld event) {
        box.mounter(WorldMounter.class).handle(event);
    }

    public void feed(DestroyWorld event) {
        box.mounter(WorldMounter.class).handle(event);
    }

    public <T extends Event> void feed(T event) {
        this.<T>feedFunctionOf(event.getClass()).accept(this, event);
    }

    @SuppressWarnings("unchecked")
    private <T extends Event> BiConsumer<EngineTerminal, T> feedFunctionOf(Class<? extends Event> eventClass) {
        return (BiConsumer<EngineTerminal, T>) FeedFunctions.getOrDefault(eventClass, this::doNothing);
    }

    private void doNothing(EngineTerminal engineTerminal, Event event) {
    }

    private static final Map<Class<? extends Event>, BiConsumer<EngineTerminal, ? extends Event>> FeedFunctions = new HashMap<>(){
        {
            put(CreateAchievement.class, (BiConsumer<EngineTerminal, CreateAchievement>)EngineTerminal::feed);
            put(DeleteAchievement.class, (BiConsumer<EngineTerminal, DeleteAchievement>)EngineTerminal::feed);
            put(Action.class, (BiConsumer<EngineTerminal, Attack>)EngineTerminal::feed);
            put(Attack.class, (BiConsumer<EngineTerminal, Attack>)EngineTerminal::feed);
            put(DisableEntity.class, (BiConsumer<EngineTerminal, DisableEntity>)EngineTerminal::feed);
            put(EnableEntity.class, (BiConsumer<EngineTerminal, EnableEntity>)EngineTerminal::feed);
            put(Heal.class, (BiConsumer<EngineTerminal, Heal>)EngineTerminal::feed);
            put(SetHealth.class, (BiConsumer<EngineTerminal, SetHealth>)EngineTerminal::feed);
            put(CreatePlayer.class, (BiConsumer<EngineTerminal, CreatePlayer>)EngineTerminal::feed);
            put(CreateNpc.class, (BiConsumer<EngineTerminal, CreateNpc>)EngineTerminal::feed);
            put(CreateItem.class, (BiConsumer<EngineTerminal, CreateItem>)EngineTerminal::feed);
            put(DestroyPlayer.class, (BiConsumer<EngineTerminal, DestroyPlayer>)EngineTerminal::feed);
            put(DestroyNpc.class, (BiConsumer<EngineTerminal, DestroyNpc>)EngineTerminal::feed);
            put(DestroyItem.class, (BiConsumer<EngineTerminal, DestroyItem>)EngineTerminal::feed);
            put(PickUpItem.class, (BiConsumer<EngineTerminal, PickUpItem>)EngineTerminal::feed);
            put(DropItem.class, (BiConsumer<EngineTerminal, DropItem>)EngineTerminal::feed);
            put(BeginMatch.class, (BiConsumer<EngineTerminal, BeginMatch>)EngineTerminal::feed);
            put(EndMatch.class, (BiConsumer<EngineTerminal, EndMatch>)EngineTerminal::feed);
            put(CreateMission.class, (BiConsumer<EngineTerminal, CreateMission>)EngineTerminal::feed);
            put(CreateWorld.class, (BiConsumer<EngineTerminal, CreateWorld>)EngineTerminal::feed);
            put(DestroyWorld.class, (BiConsumer<EngineTerminal, DestroyWorld>)EngineTerminal::feed);
        }};
}
