package org.example.cinepolis.control.box;

import io.intino.gamification.Engine;
import io.intino.magritte.framework.Graph;
import org.example.cinepolis.control.Archetype;
import org.example.cinepolis.control.box.mounters.MounterFactory;
import org.example.cinepolis.control.gamification.Adapter;
import org.example.cinepolis.control.graph.ControlGraph;

public class ControlBox extends AbstractBox {

	private final Archetype archetype;
	private Engine engine;
	private ControlGraph graph;
	private MounterFactory mounter;
	private Adapter adapter;

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
		if(o instanceof Graph) graph = ((Graph) o).as(ControlGraph.class);
		return this;
	}

	public void beforeStart() {
		this.mounter = new MounterFactory(this);
		this.adapter = new Adapter(this);
	}

	public void afterStart() {
		this.adapter.initialize();
	}

	public void beforeStop() {

	}

	public void afterStop() {

	}

	public Archetype archetype() {
		return this.archetype;
	}

	public Archetype.Datamart.Cinepolis datamart() {
		return archetype.datamart().cinepolis();
	}

	public Engine engine() {
		return this.engine;
	}

	public ControlGraph graph() {
		return this.graph;
	}

	public MounterFactory mounter() {
		return this.mounter;
	}

	public Adapter adapter() {
		return this.adapter;
	}
}