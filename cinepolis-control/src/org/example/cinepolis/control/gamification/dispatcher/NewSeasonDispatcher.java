package org.example.cinepolis.control.gamification.dispatcher;

import io.intino.gamification.graph.model.Player;
import io.intino.gamification.graph.model.World;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.gamification.model.Employee;
import org.example.cinepolis.datahub.events.gamification.NewSeason;

public class NewSeasonDispatcher extends Dispatcher<NewSeason> {

    public NewSeasonDispatcher(ControlBox box) {
        super(box);
    }

    @Override
    public void dispatch(NewSeason event) {

        World world = box.adapter().world();

        for (Player player : world.players()) {
            adjustScoreOf((Employee) player);
        }
    }

    private void adjustScoreOf(Employee employee) {
        employee.healthScore(0);
        employee.missionScore(0);
    }
}
