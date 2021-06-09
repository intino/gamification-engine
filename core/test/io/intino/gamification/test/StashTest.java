package io.intino.gamification.test;

import io.intino.gamification.Engine;
import io.intino.gamification.api.EngineDatamart;
import io.intino.gamification.api.EngineTerminal;
import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.mission.MissionDifficulty;
import io.intino.gamification.core.box.events.mission.MissionType;
import io.intino.gamification.core.box.events.mission.NewMission;
import io.intino.gamification.core.box.events.world.CreateWorld;
import io.intino.gamification.core.graph.Match;
import io.intino.gamification.core.graph.Mission;
import io.intino.magritte.framework.Layer;

import java.util.HashMap;
import java.util.Map;

public class StashTest {

    public static void main(String[] args) {

        Map<String, String> arguments = new HashMap<>();
        arguments.put("home", "./temp");
        arguments.put("datalake_path", "./temp/datalake");

        Engine engine = new Engine(arguments);

        engine.launch(() -> {
            CreateWorld cw = new CreateWorld().id("world");
            BeginMatch bm = new BeginMatch().world("world").id("match");
            NewMission nm = new NewMission().description("")
                    .difficulty(MissionDifficulty.Medium)
                    .maxCount(3)
                    .event(EventType.PickUpItem)
                    .type(MissionType.Primary)
                    .match("match")
                    .id("mission");

            EngineDatamart datamart = engine.datamart();

            engine.terminal().feed(cw);
            engine.terminal().feed(bm);
            engine.terminal().feed(nm);

            Match match = engine.datamart().match("match");
            Mission mission = match.missions(0);
            //mission.delete$();

            mission.core$().delete();

            System.out.println("nfjsnef");
        });
    }
}
