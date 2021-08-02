package org.example.cinepolis.control.box;

import old.core.box.checkers.CheckResult;
import old.core.box.events.EventType;
import old.core.box.events.achievement.CreateAchievement;
import old.core.box.events.match.BeginMatch;
import old.core.box.events.match.EndMatch;
import old.core.box.events.mission.NewStateMission;
import old.core.box.listeners.EventProcessListener;
import old.core.box.utils.TimeUtils;
import old.core.model.Achievement;
import old.core.model.Match;
import old.core.model.World;
import old.core.model.attributes.AchievementType;
import old.core.model.attributes.MissionState;
import org.example.cinepolis.control.gamification.GamificationConfig;
import org.example.cinepolis.datahub.events.cinepolis.*;

import java.util.UUID;

public class DatamartUtils {

    public static void newWorkingDay(ControlBox box) {
        World world = box.engine().datamart().world(GamificationConfig.WorldId);
        if(world != null) {
            Match match = world.match();
            if(match != null) {
                box.engine().terminal().feed(endMatch(match.id()));
            }
        }

        box.engine().terminal().feed(beginMatch());

        CreateAchievement la = (CreateAchievement) new CreateAchievement()
                .world(GamificationConfig.WorldId)
                .type(AchievementType.Local)
                .description("Arregla 3 proyectores")
                .eventInvolved(EventType.NewStateMission)
                .maxCount(3)
                .id(UUID.randomUUID().toString())
                .ts(TimeUtils.currentInstant());

        EventProcessListener<Achievement> eventProcessListener = achievement -> {
            achievement.<NewStateMission>progressIf((e, p) -> {
                if (e.player().equals(p.id()) && e.state().equals(MissionState.Completed)) {
                    return CheckResult.Progress;
                } else {
                    return CheckResult.Skip;
                }
            });
        };
        box.engine().terminal().feed(la, eventProcessListener);
    }

    public static DeregisterAsset deleteAsset(String id) {
        return new DeregisterAsset()
                .ts(TimeUtils.currentInstant())
                .id(id);
    }

    public static RegisterAsset newAsset(String id, String name, String area) {
        return new RegisterAsset()
                .ts(TimeUtils.currentInstant())
                .id(id)
                .name(name)
                .area(area);
    }

    public static HireEmployee newEmployee(String id, String name, int age, String phone, String area) {
        return new HireEmployee()
                .ts(TimeUtils.currentInstant())
                .id(id)
                .name(name)
                .age(age)
                .phone(phone)
                .area(area);
    }

    public static DismissEmployee deleteEmployee(String id) {
        return new DismissEmployee()
                .ts(TimeUtils.currentInstant())
                .id(id);
    }

    public static AssetAlert generateAlert(String id, String asset, AssetAlert.Importance importance, int limitHours, String description) {
        return new AssetAlert()
                .ts(TimeUtils.currentInstant())
                .id(id)
                .asset(asset)
                .importance(importance)
                .limitHours(limitHours)
                .description(description);
    }

    public static FixedAsset completeAlert(String alert, String asset, String employee) {
        return new FixedAsset()
                .ts(TimeUtils.currentInstant())
                .alert(alert)
                .asset(asset)
                .employee(employee);
    }

    public static BeginMatch beginMatch() {
        return (BeginMatch) new BeginMatch()
                .world(GamificationConfig.WorldId)
                .expiration(TimeUtils.nextInstant(TimeUtils.currentInstant(), TimeUtils.Scale.Minute, 2))
                .reboot(true)
                .id(UUID.randomUUID().toString())
                .ts(TimeUtils.currentInstant());
    }

    public static EndMatch endMatch(String matchId) {
        return (EndMatch) new EndMatch()
                .world(GamificationConfig.WorldId)
                .id(matchId)
                .ts(TimeUtils.currentInstant());
    }
}
