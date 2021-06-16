package org.example.smartbrain.control.box;

import org.example.smartbrain.control.box.mounters.MounterFactory;

public class ControlBox extends AbstractBox {

	private final MounterFactory mounterFactory;

	public ControlBox(String[] args) {
		this(new ControlConfiguration(args));
	}

	public ControlBox(ControlConfiguration configuration) {
		super(configuration);
		this.mounterFactory = new MounterFactory(this);
	}

	@Override
	public io.intino.alexandria.core.Box put(Object o) {
		super.put(o);
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