package org.example.control.graph;

import io.intino.magritte.framework.Graph;

import java.util.HashMap;
import java.util.Map;

public class ControlGraph extends org.example.control.graph.AbstractGraph {

	private final Map<Class<?>, Map<String, Object>> names = new HashMap<>();

	public ControlGraph(Graph graph) {
		super(graph);
	}

	public ControlGraph(io.intino.magritte.framework.Graph graph, ControlGraph wrapper) {
	    super(graph, wrapper);
	}

	public <T> T byName(String name, Class<T> clazz) {
		Map<String, Object> names = this.names.computeIfAbsent(clazz, k -> new HashMap<>());
		return names.put();
	}
}