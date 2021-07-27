package org.example.cinepolis.control.box.actions;

import io.intino.alexandria.Json;
import io.intino.alexandria.http.spark.SparkContext;
import io.intino.gamification.core.model.Player;
import org.example.cinepolis.control.box.ControlBox;


public class GetPlayerAction {

	protected static final String EMPTY_JSON = "{}";

	public ControlBox box;
	public SparkContext context;
	public String world;
	public String id;

	protected Player get() {
		return box.engine().datamart().player(world, id);
	}

	public String execute() {
		final Player response = get();
		return response == null ? EMPTY_JSON : Json.toString(response);
	}
}