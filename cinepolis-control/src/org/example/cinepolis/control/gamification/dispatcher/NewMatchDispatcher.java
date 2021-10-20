package org.example.cinepolis.control.gamification.dispatcher;

import io.intino.gamification.graph.model.Match;
import io.intino.gamification.graph.model.World;
import io.intino.gamification.util.time.TimeUtils;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.gamification.model.Employee;
import org.example.cinepolis.datahub.events.gamification.NewMatch;

import java.util.Collection;
import java.util.Map;

public class NewMatchDispatcher extends Dispatcher<NewMatch> {

    public NewMatchDispatcher(ControlBox box) {
        super(box);
    }

    @Override
    public void dispatch(NewMatch event) {

        World world = box.adapter().world();

        addMissionPointsToSeasonScore(world.currentMatch().players().values());
        startNewMatchIn(world);
    }

    private void addMissionPointsToSeasonScore(Collection<Match.PlayerState> players) {
        players.forEach(ps -> {
            Employee employee = (Employee) ps.actor();
            employee.addMissionScore(Math.max(ps.score(), 0));
        });
    }

    private void startNewMatchIn(World world) {
        Map<String, Match.PlayerState> persistencePlayerState = world.currentMatch().persistencePlayerState();
        world.finishCurrentMatch();
        world.startNewMatch(new Match(world.id(), getMatchId(), persistencePlayerState));
    }

    private String getMatchId() {
        StringBuilder sb = new StringBuilder()
                .append("match")
                .append("_")
                .append(TimeUtils.currentInstant().toString());

        return sb.toString();
    }
}
