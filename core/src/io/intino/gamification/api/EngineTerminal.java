package io.intino.gamification.api;

import io.intino.alexandria.event.Event;
import io.intino.gamification.core.model.*;
import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.model.attributes.AchievementType;
import io.intino.gamification.core.box.events.achievement.CreateAchievement;
import io.intino.gamification.core.box.events.achievement.DeleteAchievement;
import io.intino.gamification.core.box.events.action.*;
import io.intino.gamification.core.box.events.entity.*;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.match.EndMatch;
import io.intino.gamification.core.box.events.mission.CreateMission;
import io.intino.gamification.core.box.events.world.CreateWorld;
import io.intino.gamification.core.box.events.world.DestroyWorld;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class EngineTerminal {

    private final CoreBox box;

    public EngineTerminal(CoreBox box) {
        this.box = box;
    }

    public Achievement feed(CreateAchievement event) {
        box.terminal().feed(event);
        if(event.type() == AchievementType.Global) {
            return box.engineDatamart().globalAchievement(event.world(), event.id());
        } else if(event.type() == AchievementType.Local) {
            return box.engineDatamart().localAchievement(event.world(), event.id());
        }
        return null;
    }

    public void feed(DeleteAchievement event) {
        box.terminal().feed(event);
    }

    public void feed(Action event) {
        box.terminal().feed(event);
    }

    public void feed(Attack event) {
        box.terminal().feed(event);
    }

    public void feed(DisableEntity event) {
        box.terminal().feed(event);
    }

    public void feed(EnableEntity event) {
        box.terminal().feed(event);
    }

    public void feed(Heal event) {
        box.terminal().feed(event);
    }

    public void feed(SetHealth event) {
        box.terminal().feed(event);
    }

    public Player feed(CreatePlayer event) {
        box.terminal().feed(event);
        return box.engineDatamart().player(event.world(), event.id());
    }

    public Npc feed(CreateNpc event) {
        box.terminal().feed(event);
        return box.engineDatamart().npc(event.world(), event.id());
    }

    public Item feed(CreateItem event) {
        box.terminal().feed(event);
        return box.engineDatamart().item(event.world(), event.id());
    }

    public void feed(DestroyPlayer event) {
        box.terminal().feed(event);
    }

    public void feed(DestroyNpc event) {
        box.terminal().feed(event);
    }

    public void feed(DestroyItem event) {
        box.terminal().feed(event);
    }

    public void feed(PickUpItem event) {
        box.terminal().feed(event);
    }

    public void feed(DropItem event) {
        box.terminal().feed(event);
    }

    public Match feed(BeginMatch event) {
        box.terminal().feed(event);
        Match match = box.engineDatamart().match(event.world());
        return match.from().equals(event.ts()) ? match : null;
    }

    public void feed(EndMatch event) {
        box.terminal().feed(event);
    }

    public Mission feed(CreateMission event) {
        box.terminal().feed(event);
        return box.engineDatamart().mission(event.world(), event.id());
    }

    public World feed(CreateWorld event) {
        box.terminal().feed(event);
        return box.engineDatamart().world(event.id());
    }

    public void feed(DestroyWorld event) {
        box.terminal().feed(event);
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
