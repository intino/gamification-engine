/*package org.example.cinepolis.control.box.actions;

import io.intino.gamification.graph.model.Player;
import io.intino.gamification.graph.model.World;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.gamification.model.Cinema;
import org.example.cinepolis.control.gamification.model.Employee;

public class CinemaWeeklyVisitAction {

	public ControlBox box;
	private World world;

	public void execute() {

		world = box.adapter().world();

		for (Player employee : world.players()) {
			checkVisitsOf((Employee) employee);
		}
	}

	private void checkVisitsOf(Employee employee) {
		for (Cinema cinema : employee.cinemas()) {
			if(!cinema.weeklyVisitFlag()) {
				cinema.health(Math.max(0, cinema.health() - 25));
				if(cinema.health() == 0) {
					world.currentMatch().player(employee.id()).addScore(-10);
				}
			} else {
				cinema.health(100);
			}
			cinema.weeklyVisitFlag(false);
		}
	}
}*/