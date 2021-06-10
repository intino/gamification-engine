package org.example.cinepolis.control.graph;

import io.intino.magritte.framework.Graph;

public class ControlGraph extends org.example.cinepolis.control.graph.AbstractGraph {

	public ControlGraph(Graph graph) {
		super(graph);
	}

	public ControlGraph(io.intino.magritte.framework.Graph graph, ControlGraph wrapper) {
	    super(graph, wrapper);
	}
}