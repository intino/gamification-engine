package org.example.cinepolis.control.box.actions;

import io.intino.gamification.graph.model.Player;
import io.intino.gamification.graph.model.World;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.gamification.model.Employee;

public class NewSeasonAction {

	public ControlBox box;

	public void execute() {

		World world = box.adapter().world();

		for (Player player : world.players()) {
			adjustScoreOf((Employee) player);
		}
	}

	private void adjustScoreOf(Employee employee) {
		//TODO EL PROBLEMA ES QUE NO SE QUEDA REGISTRADA LA PUNTUACION DE LAS SEASONS
		employee.seasonScore(0);
	}
}