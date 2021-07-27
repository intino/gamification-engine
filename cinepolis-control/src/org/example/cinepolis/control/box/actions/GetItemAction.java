package org.example.cinepolis.control.box.actions;

import io.intino.alexandria.Json;
import io.intino.alexandria.http.spark.SparkContext;
import io.intino.gamification.core.model.Item;
import org.example.cinepolis.control.box.ControlBox;


public class GetItemAction {

	protected static final String EMPTY_JSON = "{}";

	public ControlBox box;
	public SparkContext context;
	public String world;
	public String id;

	protected Item get() {
		return box.engine().datamart().item(world, id);
	}

	public String execute() {
		final Item response = get();
		return response == null ? EMPTY_JSON : Json.toString(response);
	}
}