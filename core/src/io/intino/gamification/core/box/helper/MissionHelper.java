package io.intino.gamification.core.box.helper;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.EventBuilder;
import io.intino.gamification.core.graph.*;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.intino.gamification.core.box.events.mission.MissionState.Failed;

public class MissionHelper extends Helper {

    public MissionHelper(CoreBox box) {
        super(box);
    }

    public void failMissions(World world, Predicate<Mission> missionFilter) {
        Match match = world.match();

        List<Mission> missionsToExpire = match.missions().stream()
                .filter(missionFilter)
                .collect(Collectors.toList());

        missionsToExpire.forEach(m -> {
            match.players().stream()
                    .filter(p -> m.players().contains(p.id()))
                    .filter(AbstractEntity::enabled)
                    .forEach(p -> failMissions(world, m, p));
        });
    }

    private void failMissions(World world, Mission mission, Player player) {
        box.engineTerminal().feed(EventBuilder.newStateMission(world.id(), mission.id(), player.id(), Failed));
    }
}
