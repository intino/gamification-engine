package io.intino.gamification.core.box;

import io.intino.gamification.api.EngineConfiguration;
import io.intino.gamification.api.EngineDatamart;
import io.intino.gamification.api.EngineTerminal;
import io.intino.gamification.core.box.checkers.Checker;
import io.intino.gamification.core.box.checkers.Checkers;
import io.intino.gamification.core.box.events.Terminal;
import io.intino.gamification.core.box.helper.Helper;
import io.intino.gamification.core.box.helper.Helpers;
import io.intino.gamification.core.box.mounter.Mounter;
import io.intino.gamification.core.box.mounter.Mounters;
import io.intino.gamification.core.graph.CoreGraph;
import io.intino.magritte.framework.Graph;

import java.io.File;

public class CoreBox extends AbstractBox {

	private CoreGraph graph;
	private Terminal terminal;
	private EngineConfiguration engineConfig;
	private EngineTerminal engineTerminal;
	private EngineDatamart engineDatamart;
	private Mounters mounters;
	private Checkers checkers;
	private Helpers helpers;

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
		this.engineConfig = new EngineConfiguration();
		if(graph != null) this.graph.engineConfig(engineConfig);
		this.terminal = new Terminal(this);
		this.engineTerminal = new EngineTerminal(this);
		this.engineDatamart = new EngineDatamart(this);
		this.mounters = new Mounters(this);
		this.checkers = new Checkers(this);
		this.helpers = new Helpers(this);
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

	public Terminal terminal() {
		return terminal;
	}

	public EngineConfiguration engineConfig() {
		return engineConfig;
	}

	public EngineTerminal engineTerminal() {
		return engineTerminal;
	}

	public EngineDatamart engineDatamart() {
		return engineDatamart;
	}

	public <L extends Mounter> L mounter(Class<L> clazz) {
		return mounters.mounter(clazz);
	}

	public <L extends Checker> L checker(Class<L> clazz) {
		return checkers.checker(clazz);
	}

	public <L extends Helper> L helper(Class<L> clazz) {
		return helpers.helper(clazz);
	}
}