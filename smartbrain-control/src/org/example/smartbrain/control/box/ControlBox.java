package org.example.smartbrain.control.box;

import io.intino.gamification.Engine;
import org.example.smartbrain.control.Archetype;
import org.example.smartbrain.control.box.mounters.MounterFactory;
import org.example.smartbrain.control.graph.ControlGraph;

public class ControlBox extends AbstractBox {

	private final MounterFactory mounterFactory;
	private final Archetype archetype;
	private ControlGraph graph;
	private Engine engine;

	public ControlBox(String[] args) {
		this(new ControlConfiguration(args));
	}

	public ControlBox(ControlConfiguration configuration) {
		super(configuration);
		this.mounterFactory = new MounterFactory(this);
		this.archetype = new Archetype(configuration.home());
	}

	@Override
	public io.intino.alexandria.core.Box put(Object o) {
		super.put(o);
		if(o instanceof ControlGraph) graph = (ControlGraph) o;
		if(o instanceof Engine) engine = (Engine) o;
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

	public Archetype.Datamart.Smartbrain datamart() {
		return archetype.datamart().smartbrain();
	}

	public Engine engine() {
		return engine;
	}

	public MounterFactory mounters() {
		return this.mounterFactory;
	}

	public ControlGraph graph() {
		return this.graph;
	}
}