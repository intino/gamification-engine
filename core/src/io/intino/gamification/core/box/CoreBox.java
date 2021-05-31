package io.intino.gamification.core.box;

import io.intino.gamification.core.Archetype;
import io.intino.gamification.core.box.mounter.Mounter;
import io.intino.gamification.core.box.mounter.Mounters;
import io.intino.gamification.core.box.terminal.Terminal;

public class CoreBox extends AbstractBox {

	private final Archetype archetype;
	private Terminal terminal;
	private Mounters mounters;

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
		this.mounters = new Mounters(this);
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

	public <L extends Mounter> L mounter(Class<L> clazz) {
		return mounters.mounter(clazz);
	}
}