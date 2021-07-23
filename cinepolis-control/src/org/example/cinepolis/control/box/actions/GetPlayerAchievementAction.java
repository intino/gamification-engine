package org.example.cinepolis.control.box.actions;

import io.intino.gamification.core.graph.Achievement;


public class GetPlayerAchievementAction extends AbstractGetAction<Achievement> {

	public String world;
	public String id;
	public String player;

	@Override
	protected Achievement get() {
		return null;
	}
}