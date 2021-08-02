package org.example.cinepolis.control.box.actions;

import io.intino.alexandria.Json;
import io.intino.alexandria.http.spark.SparkContext;
import old.core.model.Achievement;
import org.example.cinepolis.control.box.ControlBox;

public class GetAchievementAction {

	protected static final String EMPTY_JSON = "{}";

	public ControlBox box;
	public SparkContext context;
	public String world;
	public String id;

	protected Achievement get() {
		return box.engine().datamart().globalAchievement(world, id);
	}

	public String execute() {
		final Achievement response = get();
		return response == null ? EMPTY_JSON : Json.toString(response);
	}
}