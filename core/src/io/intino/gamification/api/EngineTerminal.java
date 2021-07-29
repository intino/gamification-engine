package io.intino.gamification.api;

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
import io.intino.gamification.core.box.listeners.EventProcessListener;
import io.intino.gamification.core.model.*;

public class EngineTerminal {

    private final CoreBox box;

    public EngineTerminal(CoreBox box) {
        this.box = box;
    }

    public void feed(CreateAchievement event, EventProcessListener<Achievement> eventProcessListener) {
        box.engineConfig().gameLoopConfigurator.enqueue(event, eventProcessListener);
    }

    public void feed(DeleteAchievement event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(Action event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(Attack event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(DisableEntity event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(EnableEntity event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(Heal event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(SetHealth event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(CreatePlayer event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(CreatePlayer event, EventProcessListener<Player> eventProcessListener) {
        box.engineConfig().gameLoopConfigurator.enqueue(event, eventProcessListener);
    }

    public void feed(CreateNpc event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(CreateNpc event, EventProcessListener<Npc> eventProcessListener) {
        box.engineConfig().gameLoopConfigurator.enqueue(event, eventProcessListener);
    }

    public void feed(CreateItem event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(CreateItem event, EventProcessListener<Item> eventProcessListener) {
        box.engineConfig().gameLoopConfigurator.enqueue(event, eventProcessListener);
    }

    public void feed(DestroyPlayer event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(DestroyNpc event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(DestroyItem event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(PickUpItem event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(DropItem event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(BeginMatch event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(BeginMatch event, EventProcessListener<Match> eventProcessListener) {
        box.engineConfig().gameLoopConfigurator.enqueue(event, eventProcessListener);
    }

    public void feed(EndMatch event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(CreateMission event, EventProcessListener<Mission> eventProcessListener) {
        box.engineConfig().gameLoopConfigurator.enqueue(event, eventProcessListener);
    }

    public void feed(CreateWorld event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }

    public void feed(CreateWorld event, EventProcessListener<World> eventProcessListener) {
        box.engineConfig().gameLoopConfigurator.enqueue(event, eventProcessListener);
    }

    public void feed(DestroyWorld event) {
        box.engineConfig().gameLoopConfigurator.enqueue(event);
    }
}
