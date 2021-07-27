package org.example.cinepolis.control.box.actions;

import io.intino.alexandria.Json;
import io.intino.alexandria.http.spark.SparkContext;
import io.intino.gamification.core.graph.Achievement;
import org.example.cinepolis.control.box.ControlBox;


public class GetPlayerAchievementAction {

	protected static final String EMPTY_JSON = "{}";

	public ControlBox box;
	public SparkContext context;
	public String world;
	public String id;
	public String player;

	protected Achievement get() {
		return null;
	}

	public String execute() {
		final Achievement response = get();
		return response == null ? EMPTY_JSON : Json.toString(response);
	}
}