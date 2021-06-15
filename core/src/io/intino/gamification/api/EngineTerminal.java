package io.intino.gamification.api;

import io.intino.alexandria.event.Event;
import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.achievement.AchievementNewState;
import io.intino.gamification.core.box.events.achievement.CreateAchievement;
import io.intino.gamification.core.box.events.achievement.DeleteAchievement;
import io.intino.gamification.core.box.events.entity.*;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.match.EndMatch;
import io.intino.gamification.core.box.events.mission.NewMission;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.box.events.world.CreateWorld;
import io.intino.gamification.core.box.events.world.DestroyWorld;
import io.intino.gamification.core.box.mounter.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class EngineTerminal {

    private final CoreBox box;

    public EngineTerminal(CoreBox box) {
        this.box = box;
    }

    public void feed(CreateWorld event) {
        box.mounter(WorldMounter.class).handle(event);
    }

    public void feed(DestroyWorld event) {
        box.mounter(WorldMounter.class).handle(event);
    }

    public void feed(BeginMatch event) {
        box.mounter(MatchMounter.class).handle(event);
    }

    public void feed(EndMatch event) {
        box.mounter(MatchMounter.class).handle(event);
    }

    public void feed(NewMission event) {
        box.mounter(MissionMounter.class).handle(event);
    }

    public void feed(NewStateMission event) {
        box.mounter(MissionMounter.class).handle(event);
    }

    public void feed(CreatePlayer event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(CreateEnemy event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(CreateNpc event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(CreateItem event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(DestroyEntity event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(PickUpItem event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(DropItem event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(Action event) {
        box.mounter(EntityMounter.class).handle(event);
    }

    public void feed(CreateAchievement event) {
        box.mounter(AchievementMounter.class).handle(event);
    }

    public void feed(DeleteAchievement event) {
        box.mounter(AchievementMounter.class).handle(event);
    }

    public void feed(AchievementNewState event) {
        box.mounter(AchievementMounter.class).handle(event);
    }

    public <T extends Event> void feed(T event) {
        feedFunctionOf(event.getClass()).accept(this, event);
    }

    @SuppressWarnings("unchecked")
    private <T extends Event> BiConsumer<EngineTerminal, T> feedFunctionOf(Class<? extends Event> eventClass) {
        return (BiConsumer<EngineTerminal, T>) FeedFunctions.getOrDefault(eventClass, this::doNothing);
    }

    private void doNothing(EngineTerminal engineTerminal, Event event) {
    }

    private static final Map<Class<? extends Event>, BiConsumer<EngineTerminal, ? extends Event>> FeedFunctions = new HashMap<>(){
        {
            put(CreateWorld.class, EngineTerminal::feed);
            put(DestroyWorld.class, EngineTerminal::feed);
            put(BeginMatch.class, EngineTerminal::feed);
            put(EndMatch.class, EngineTerminal::feed);
            put(NewMission.class, EngineTerminal::feed);
            put(NewStateMission.class, EngineTerminal::feed);
            put(CreatePlayer.class, EngineTerminal::feed);
            put(CreateEnemy.class, EngineTerminal::feed);
            put(CreateNpc.class, EngineTerminal::feed);
            put(CreateItem.class, EngineTerminal::feed);
            put(DestroyEntity.class, EngineTerminal::feed);
            put(PickUpItem.class, EngineTerminal::feed);
            put(DropItem.class, EngineTerminal::feed);
            put(Action.class, EngineTerminal::feed);
            put(CreateAchievement.class, EngineTerminal::feed);
            put(DeleteAchievement.class, EngineTerminal::feed);
            put(AchievementNewState.class,EngineTerminal::feed);
        }};
}
