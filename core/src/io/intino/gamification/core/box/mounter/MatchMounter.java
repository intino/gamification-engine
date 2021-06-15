package io.intino.gamification.core.box.mounter;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.EventBuilder;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.achievement.AchievementState;
import io.intino.gamification.core.box.events.match.BeginMatch;
import io.intino.gamification.core.box.events.match.EndMatch;
import io.intino.gamification.core.box.mounter.builder.MatchFilter;
import io.intino.gamification.core.graph.*;

import java.util.List;

import static io.intino.gamification.core.box.events.match.MatchState.Finished;
import static io.intino.gamification.core.box.events.mission.MissionState.Failed;

public class MatchMounter extends Mounter {

    public MatchMounter(CoreBox box) {
        super(box);
    }

    @Override
    public void mount(GamificationEvent event) {
        if(event instanceof BeginMatch) handle((BeginMatch) event);
        if(event instanceof EndMatch) handle((EndMatch) event);
    }

    private void handle(BeginMatch event) {
        MatchFilter filter = new MatchFilter(box, event);
        if(!filter.beginMatchCanMount()) return;

        World world = filter.world();
        Match match = box.graph().match(event, world.id());

        world.match(match);

        world.save$();
        match.save$();
    }

    private void handle(EndMatch event) {
        MatchFilter filter = new MatchFilter(box, event);
        if(!filter.endMatchCanMount()) return;

        World world = filter.world();
        Match match = filter.match();

        world.match(null);
        match.to(event.ts()).state(Finished);
        fail(match);

        world.save$();
        match.save$();
    }

    private void fail(Match match) {
        match.players().stream().filter(AbstractEntity::enabled).forEach(p -> {
            failMissions(match.missions(), match.worldId(), p.id());
        });
    }

    private void failMissions(List<Mission> missions, String worldId, String playerId) {
        missions.forEach(m -> box.engineTerminal().feed(EventBuilder.newStateMission(worldId, m.id(), playerId, Failed)));
    }
}