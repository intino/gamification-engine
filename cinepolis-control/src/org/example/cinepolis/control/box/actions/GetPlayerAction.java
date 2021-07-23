package org.example.cinepolis.control.box.actions;

import io.intino.gamification.core.model.Player;


public class GetPlayerAction extends AbstractGetAction<Player> {

	public String world;
	public String id;

	@Override
	protected Player get() {
		return box.engine().datamart().player(world, id);
	}
}