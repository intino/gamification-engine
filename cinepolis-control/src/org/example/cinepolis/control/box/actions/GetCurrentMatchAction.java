package org.example.cinepolis.control.box.actions;

import io.intino.alexandria.Json;
import io.intino.alexandria.http.spark.SparkContext;
import old.core.model.Match;
import org.example.cinepolis.control.box.ControlBox;


public class GetCurrentMatchAction {

	protected static final String EMPTY_JSON = "{}";

	public ControlBox box;
	public SparkContext context;
	public String world;

	protected Match get() {
		return box.engine().datamart().match(world);
	}

	public String execute() {
		final Match response = get();
		return response == null ? EMPTY_JSON : Json.toString(response);
	}
}