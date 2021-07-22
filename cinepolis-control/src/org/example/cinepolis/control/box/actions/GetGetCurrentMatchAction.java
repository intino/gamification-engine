package org.example.cinepolis.control.box.actions;

import io.intino.gamification.core.model.Match;


public class GetGetCurrentMatchAction extends AbstractGetAction<Match> {

	public String world;

	@Override
	protected Match get() {
		return datamart().match(world);
	}

}