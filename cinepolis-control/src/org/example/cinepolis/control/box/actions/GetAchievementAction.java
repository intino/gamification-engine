package org.example.cinepolis.control.box.actions;

import io.intino.gamification.core.model.Achievement;


public class GetAchievementAction extends AbstractGetAction<Achievement> {

	public String world;
	public String id;

	@Override
	protected Achievement get() {
		return datamart().globalAchievement(world, id);
	}
}