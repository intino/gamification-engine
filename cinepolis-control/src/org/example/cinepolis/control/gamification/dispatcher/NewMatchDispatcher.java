package org.example.cinepolis.control.gamification.dispatcher;

import io.intino.gamification.graph.model.Round;
import io.intino.gamification.graph.model.Competition;
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

        Competition competition = box.adapter().world();

        addMissionPointsToSeasonScore(competition.currentSeason().players().values());
        startNewMatchIn(competition);
    }

    private void addMissionPointsToSeasonScore(Collection<Round.Match> players) {
        players.forEach(ps -> {
            Employee employee = (Employee) ps.actor();
            employee.addMissionScore(Math.max(ps.score(), 0));
        });
    }

    private void startNewMatchIn(Competition competition) {
        Map<String, Round.Match> persistencePlayerState = competition.currentSeason().persistencePlayerState();
        competition.finishCurrentMatch();
        competition.startNewSeason(new Round(competition.id(), getMatchId(), persistencePlayerState));
    }

    private String getMatchId() {
        StringBuilder sb = new StringBuilder()
                .append("match")
                .append("_")
                .append(TimeUtils.currentInstant().toString());

        return sb.toString();
    }
}
