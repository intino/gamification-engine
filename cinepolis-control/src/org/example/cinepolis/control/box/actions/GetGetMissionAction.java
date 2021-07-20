package org.example.cinepolis.control.box.actions;


import io.intino.gamification.core.model.Mission;

public class GetGetMissionAction extends AbstractGetAction<Mission> {

	public String world;
	public String id;

	@Override
	protected Mission get() {
		return box.engine().datamart().mission(world, id);
	}
}