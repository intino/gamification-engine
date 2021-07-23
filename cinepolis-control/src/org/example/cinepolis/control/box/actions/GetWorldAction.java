package org.example.cinepolis.control.box.actions;

import io.intino.gamification.core.model.World;


public class GetWorldAction extends AbstractGetAction<World> {

	public String id;

	protected World get() {
		return box.engine().datamart().world(id);
	}
}