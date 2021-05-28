package io.intino.gamification.core.box;

import io.intino.gamification.core.Archetype;
import io.intino.gamification.core.box.mounters.MounterFactory;

public class CoreBox extends AbstractBox {

	private final Archetype archetype;
	private MounterFactory mounterFactory;

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
		this.mounterFactory = new MounterFactory(this);
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

	public MounterFactory mounterFactory() {
		return mounterFactory;
	}
}