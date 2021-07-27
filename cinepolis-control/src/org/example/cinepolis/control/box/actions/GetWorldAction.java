package org.example.cinepolis.control.box.actions;

import io.intino.alexandria.Json;
import io.intino.alexandria.http.spark.SparkContext;
import io.intino.gamification.core.model.World;
import org.example.cinepolis.control.box.ControlBox;


public class GetWorldAction {

	protected static final String EMPTY_JSON = "{}";

	public ControlBox box;
	public SparkContext context;
	public String id;

	protected World get() {
		return box.engine().datamart().world(id);
	}

	public String execute() {
		final World response = get();
		return response == null ? EMPTY_JSON : Json.toString(response);
	}
}