package io.intino.gamification.core.box;

import io.intino.gamification.api.EngineDatamart;
import io.intino.gamification.api.EngineTerminal;
import io.intino.gamification.core.Archetype;
import io.intino.gamification.core.box.mounter.Mounter;
import io.intino.gamification.core.box.mounter.Mounters;
import io.intino.gamification.core.graph.CoreGraph;
import io.intino.magritte.framework.Graph;

public class CoreBox extends AbstractBox {

	private final Archetype archetype;
	private CoreGraph graph;
	private EngineTerminal terminal;
	private EngineDatamart datamart;
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
		if(o instanceof Graph) graph = ((Graph) o).as(CoreGraph.class);
		return this;
	}

	public void beforeStart() {
		this.terminal = new EngineTerminal(this);
		this.datamart = new EngineDatamart(this);
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

	public CoreGraph graph() {
		return graph;
	}

	public EngineTerminal engineTerminal() {
		return terminal;
	}

	public EngineDatamart engineDatamart() {
		return datamart;
	}

	public <L extends Mounter> L mounter(Class<L> clazz) {
		return mounters.mounter(clazz);
	}
}