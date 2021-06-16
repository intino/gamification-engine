package io.intino.gamification.core.box;

import io.intino.gamification.api.EngineConfiguration;
import io.intino.gamification.api.EngineDatamart;
import io.intino.gamification.api.EngineTerminal;
import io.intino.gamification.core.box.mounter.Mounter;
import io.intino.gamification.core.box.mounter.Mounters;
import io.intino.gamification.core.graph.CoreGraph;
import io.intino.magritte.framework.Graph;

import java.io.File;

public class CoreBox extends AbstractBox {

	private CoreGraph graph;
	private EngineConfiguration engineConfiguration;
	private EngineTerminal terminal;
	private EngineDatamart datamart;
	private Mounters mounters;

	public CoreBox(String[] args) {
		this(new CoreConfiguration(args));
	}

	public CoreBox(CoreConfiguration configuration) {
		super(configuration);
	}

	@Override
	public io.intino.alexandria.core.Box put(Object o) {
		super.put(o);
		if(o instanceof Graph) graph = ((Graph) o).as(CoreGraph.class);
		return this;
	}

	public void beforeStart() {
		this.engineConfiguration = new EngineConfiguration(this);
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

	public File datamart() {
		return new File(configuration.datamartPath());
	}

	public CoreGraph graph() {
		return graph;
	}

	public EngineConfiguration engineConfiguration() {
		return engineConfiguration;
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