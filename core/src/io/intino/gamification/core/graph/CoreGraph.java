package io.intino.gamification.core.graph;

import com.google.gson.Gson;
import io.intino.gamification.core.box.events.Entity;
import io.intino.gamification.core.graph.rules.Type;
import io.intino.magritte.framework.Graph;

import java.util.ArrayList;

public class CoreGraph extends io.intino.gamification.core.graph.AbstractGraph {

	public CoreGraph(Graph graph) {
		super(graph);
	}

	public CoreGraph(io.intino.magritte.framework.Graph graph, CoreGraph wrapper) {
	    super(graph, wrapper);
	}

	public io.intino.gamification.core.graph.Entity entity(Entity event) {
		return create("Entity").entity(event.id(), Type.valueOf(event.type().name()), new Gson().toJson(event.attributes()), null, new ArrayList<>());
	}
}