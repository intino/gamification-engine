package org.example.cinepolis.control.box.actions;

import io.intino.alexandria.Json;
import io.intino.alexandria.http.spark.SparkContext;
import old.core.model.Npc;
import org.example.cinepolis.control.box.ControlBox;


public class GetNpcAction {

	protected static final String EMPTY_JSON = "{}";

	public ControlBox box;
	public SparkContext context;
	public String world;
	public String id;

	protected Npc get() {
		return box.engine().datamart().npc(world, id);
	}

	public String execute() {
		final Npc response = get();
		return response == null ? EMPTY_JSON : Json.toString(response);
	}
}