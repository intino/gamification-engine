package org.example.vaccine.control.box;

import io.intino.gamification.Engine;
import io.intino.gamification.core.Archetype;
import io.intino.gamification.core.box.events.world.CreateWorld;
import org.example.vaccine.control.box.gamification.GameWorld;
import org.example.vaccine.control.box.mounters.MounterFactory;
import org.example.vaccine.control.graph.ControlGraph;

public class ControlBox extends AbstractBox {

	private final Archetype archetype;
	private Engine engine;
	private ControlGraph graph;
	private MounterFactory mounter;

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
		if(o instanceof ControlGraph) graph = (ControlGraph) o;
		return this;
	}

	public void beforeStart() {
		this.mounter = new MounterFactory(this);
		initGamificationDatamart();
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

	public MounterFactory mounter() {
		return mounter;
	}

	private void initGamificationDatamart() {
		if(hospitalsWorldDoesNotExist()) createHospitalsWorld();
	}

	private boolean hospitalsWorldDoesNotExist() {
		return engine().datamart().world(GameWorld.getID()) == null;
	}

	private void createHospitalsWorld() {
		CreateWorld world = new CreateWorld();
		world.id(GameWorld.getID());
		engine().terminal().feed(world);
	}
}