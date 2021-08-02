package old.core.box.helper;

import old.core.box.CoreBox;
import old.core.box.events.EventBuilder;
import old.core.graph.*;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static old.core.model.attributes.MissionState.Failed;

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
        box.terminal().feed(EventBuilder.newStateMission(world.id(), mission.id(), player.id(), Failed));
    }
}
