package org.example.cinepolis.control.box.actions;

import io.intino.gamification.core.model.Npc;


public class GetNpcAction extends AbstractGetAction<Npc> {

	public String world;
	public String id;

	@Override
	protected Npc get() {
		return box.engine().datamart().npc(world, id);
	}
}