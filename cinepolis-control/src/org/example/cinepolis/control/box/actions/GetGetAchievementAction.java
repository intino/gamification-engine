package org.example.cinepolis.control.box.actions;

import io.intino.gamification.core.model.Achievement;


public class GetGetAchievementAction extends AbstractGetAction<Achievement> {

	public String world;
	public String id;

	@Override
	protected Achievement get() {
		return datamart().globalAchievement(world, id);
	}
}