package org.example.vaccine.control.box;

import io.intino.gamification.GamificationEngine;
import io.intino.gamification.core.box.events.world.CreateWorld;
import org.example.vaccine.control.Archetype;
import org.example.vaccine.control.box.gamification.GameWorld;
import org.example.vaccine.control.box.mounters.MounterFactory;
import org.example.vaccine.control.graph.ControlGraph;

public class ControlBox extends AbstractBox {

	private final Archetype archetype;
	private GamificationEngine engine;
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
		if(o instanceof GamificationEngine) engine = (GamificationEngine) o;
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

	public Archetype.Datamart.Vaccine datamart() {
		return archetype.datamart().vaccine();
	}

	public GamificationEngine engine() {
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