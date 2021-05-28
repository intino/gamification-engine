package org.example.control.box;

import io.intino.magritte.framework.Graph;

public class ControlBox extends AbstractBox {

	public ControlBox(String[] args) {
		this(new ControlConfiguration(args));
	}

	public ControlBox(ControlConfiguration configuration) {
		super(configuration);
	}

	@Override
	public io.intino.alexandria.core.Box put(Object o) {
		super.put(o);
		//if(o instanceof Graph) graph = ((Graph) o).as(GamificationGraph.class);
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
}