package org.example.control.box;

import groovyjarjarantlr4.v4.misc.Graph;
import io.intino.gamification.Engine;
import io.intino.gamification.core.Archetype;
import org.example.control.graph.ControlGraph;

public class ControlBox extends AbstractBox {

	private final Archetype archetype;
	private Engine engine;
	private ControlGraph graph;

	public ControlBox(String[] args) {
		this(new ControlConfiguration(args));
	}

	public ControlBox(ControlConfiguration configuration) {
		super(configuration);
		this.archetype = new Archetype(configuration.home());
	}

	@Override
	public io.intino.alexandria.core.Box put(Object o) {
		super.put(o);
		if(o instanceof Engine) engine = (Engine) o;
		if(o instanceof Graph) graph = ((ControlGraph) o).a$(ControlGraph.class);
		return this;
	}

	public void beforeStart() {

	}

	public void afterStart() {

	}

	public void beforeStop() {

	}

	public void afterStop() {

	}

	public Archetype archetype() {
		return this.archetype;
	}

	public Archetype.Datamart.Example datamart() {
		return archetype.datamart().example();
	}

	public Engine engine() {
		return this.engine;
	}

	public ControlGraph graph() {
		return this.graph;
	}
}