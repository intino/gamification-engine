package io.intino.gamification.core.box;

import io.intino.gamification.core.Archetype;
import io.intino.gamification.core.box.terminal.Terminal;

public class CoreBox extends AbstractBox {

	private final Archetype archetype;
	private Terminal terminal;

	public CoreBox(String[] args) {
		this(new CoreConfiguration(args));
	}

	public CoreBox(CoreConfiguration configuration) {
		super(configuration);
		this.archetype = new Archetype(configuration.home());
	}

	@Override
	public io.intino.alexandria.core.Box put(Object o) {
		super.put(o);
		return this;
	}

	public void beforeStart() {
		this.terminal = new Terminal(this);
	}

	public void afterStart() {

	}

	public void beforeStop() {

	}

	public void afterStop() {

	}

	public Archetype.Datamart.Gamification datamart() {
		return archetype.datamart().gamification();
	}

	public Terminal terminal() {
		return terminal;
	}
}