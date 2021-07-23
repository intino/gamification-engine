package org.example.cinepolis.control.box.actions;

import io.intino.gamification.core.model.Item;


public class GetItemAction extends AbstractGetAction<Item> {

	public String world;
	public String id;

	@Override
	protected Item get() {
		return datamart().item(world, id);
	}
}