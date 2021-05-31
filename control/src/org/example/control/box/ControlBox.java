package org.example.control.box;

import io.intino.gamification.Engine;
import io.intino.gamification.core.Archetype;

public class ControlBox extends AbstractBox {

	private final Archetype archetype;
	private Engine engine;

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
		//if(o instanceof Graph) graph = ((Graph) o).as(GamificationGraph.class);
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

	public Archetype.Datamart.Example datamart() {
		return archetype.datamart().example();
	}
}