package old.core.box.events;

import old.core.box.CoreBox;
import old.core.box.events.achievement.AchievementNewState;
import old.core.box.events.achievement.CreateAchievement;
import old.core.box.events.achievement.DeleteAchievement;
import old.core.box.events.action.*;
import old.core.box.events.entity.*;
import old.core.box.events.match.BeginMatch;
import old.core.box.events.match.EndMatch;
import old.core.box.events.mission.CreateMission;
import old.core.box.events.mission.NewStateMission;
import old.core.box.events.world.CreateWorld;
import old.core.box.events.world.DestroyWorld;
import old.core.box.mounter.*;
import old.core.model.Component;
import old.core.model.Match;
import old.core.model.attributes.AchievementType;

public class Terminal {

    private final CoreBox box;

    public Terminal(CoreBox box) {
        this.box = box;
    }

    public Component feed(GamificationEvent event) {
        if(event instanceof AchievementNewState) return feed((AchievementNewState) event);
        else if(event instanceof CreateAchievement) return feed((CreateAchievement) event);
        else if(event instanceof DeleteAchievement) return feed((DeleteAchievement) event);
        else if(event instanceof Action) return feed((Action) event);
        else if(event instanceof Attack) return feed((Attack) event);
        else if(event instanceof DisableEntity) return feed((DisableEntity) event);
        else if(event instanceof EnableEntity) return feed((EnableEntity) event);
        else if(event instanceof Heal) return feed((Heal) event);
        else if(event instanceof SetHealth) return feed((SetHealth) event);
        else if(event instanceof ChangeScore) return feed((ChangeScore) event);
        else if(event instanceof CreatePlayer) return feed((CreatePlayer) event);
        else if(event instanceof CreateNpc) return feed((CreateNpc) event);
        else if(event instanceof CreateItem) return feed((CreateItem) event);
        else if(event instanceof DestroyPlayer) return feed((DestroyPlayer) event);
        else if(event instanceof DestroyNpc) return feed((DestroyNpc) event);
        else if(event instanceof DestroyItem) return feed((DestroyItem) event);
        else if(event instanceof PickUpItem) return feed((PickUpItem) event);
        else if(event instanceof DropItem) return feed((DropItem) event);
        else if(event instanceof BeginMatch) return feed((BeginMatch) event);
        else if(event instanceof EndMatch) return feed((EndMatch) event);
        else if(event instanceof CreateMission) return feed((CreateMission) event);
        else if(event instanceof NewStateMission) return feed((NewStateMission) event);
        else if(event instanceof CreateWorld) return feed((CreateWorld) event);
        else if(event instanceof DestroyWorld) return feed((DestroyWorld) event);
        return null;
    }

    private Component feed(AchievementNewState event) {
        box.mounter(AchievementMounter.class).handle(event);
        return null;
    }

    private Component feed(CreateAchievement event) {

        box.mounter(AchievementMounter.class).handle(event);

        if(event.type() == AchievementType.Global) {
            return box.engineDatamart().globalAchievement(event.world(), event.id());
        } else if(event.type() == AchievementType.Local) {
            return box.engineDatamart().localAchievement(event.world(), event.id());
        }
        return null;
    }

    private Component feed(DeleteAchievement event) {
        box.mounter(AchievementMounter.class).handle(event);
        return null;
    }

    private Component feed(Action event) {
        box.mounter(ActionMounter.class).handle(event);
        return null;
    }

    private Component feed(Attack event) {
        box.mounter(ActionMounter.class).handle(event);
        return null;
    }

    private Component feed(DisableEntity event) {
        box.mounter(ActionMounter.class).handle(event);
        return null;
    }

    private Component feed(EnableEntity event) {
        box.mounter(ActionMounter.class).handle(event);
        return null;
    }

    private Component feed(Heal event) {
        box.mounter(ActionMounter.class).handle(event);
        return null;
    }

    private Component feed(SetHealth event) {
        box.mounter(ActionMounter.class).handle(event);
        return null;
    }

    private Component feed(ChangeScore event) {
        box.mounter(ActionMounter.class).handle(event);
        return null;
    }

    private Component feed(CreatePlayer event) {
        box.mounter(EntityMounter.class).handle(event);
        return box.engineDatamart().player(event.world(), event.id());
    }

    private Component feed(CreateNpc event) {
        box.mounter(EntityMounter.class).handle(event);
        return box.engineDatamart().npc(event.world(), event.id());
    }

    private Component feed(CreateItem event) {
        box.mounter(EntityMounter.class).handle(event);
        return box.engineDatamart().item(event.world(), event.id());
    }

    private Component feed(DestroyPlayer event) {
        box.mounter(EntityMounter.class).handle(event);
        return null;
    }

    private Component feed(DestroyNpc event) {
        box.mounter(EntityMounter.class).handle(event);
        return null;
    }

    private Component feed(DestroyItem event) {
        box.mounter(EntityMounter.class).handle(event);
        return null;
    }

    private Component feed(PickUpItem event) {
        box.mounter(EntityMounter.class).handle(event);
        return null;
    }

    private Component feed(DropItem event) {
        box.mounter(EntityMounter.class).handle(event);
        return null;
    }

    private Component feed(BeginMatch event) {
        box.mounter(MatchMounter.class).handle(event);
        Match match = box.engineDatamart().match(event.world());
        return match.from().equals(event.ts()) ? match : null;
    }

    private Component feed(EndMatch event) {
        box.mounter(MatchMounter.class).handle(event);
        return null;
    }

    private Component feed(CreateMission event) {
        box.mounter(MissionMounter.class).handle(event);
        return box.engineDatamart().mission(event.world(), event.id());
    }

    private Component feed(NewStateMission event) {
        box.mounter(MissionMounter.class).handle(event);
        return null;
    }

    private Component feed(CreateWorld event) {
        box.mounter(WorldMounter.class).handle(event);
        return box.engineDatamart().world(event.id());
    }

    private Component feed(DestroyWorld event) {
        box.mounter(WorldMounter.class).handle(event);
        return null;
    }
}
