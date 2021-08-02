package org.example.cinepolis.control.box.actions;

import io.intino.alexandria.Json;
import io.intino.alexandria.http.spark.SparkContext;
import old.core.model.Mission;
import org.example.cinepolis.control.box.ControlBox;


public class GetMissionAction {

	protected static final String EMPTY_JSON = "{}";

	public ControlBox box;
	public SparkContext context;
	public String world;
	public String id;

	protected Mission get() {
		return box.engine().datamart().mission(world, id);
	}

	public String execute() {
		final Mission response = get();
		return response == null ? EMPTY_JSON : Json.toString(response);
	}
}