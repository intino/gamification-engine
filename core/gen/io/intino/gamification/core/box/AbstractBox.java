package io.intino.gamification.core.box;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import io.intino.alexandria.logger.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

public abstract class AbstractBox extends io.intino.alexandria.core.Box {
	protected CoreConfiguration configuration;
	protected io.intino.alexandria.terminal.Connector connector;
	protected io.intino.gamification.model.GamificationTerminal terminal;
	protected io.intino.alexandria.datalake.Datalake datalake;

	public AbstractBox(String[] args) {
		this(new CoreConfiguration(args));
	}

	public AbstractBox(CoreConfiguration configuration) {
		this.configuration = configuration;
		initJavaLogger();
		this.connector = new io.intino.alexandria.terminal.JmsConnector(configuration().get("datahub_url"), configuration().get("datahub_user"), configuration().get("datahub_password"), configuration().get("datahub_clientId"), configuration().get("datahub_outbox_directory") == null ? null : new java.io.File(configuration().get("datahub_outbox_directory")));
		this.datalake = new io.intino.alexandria.datalake.file.FileDatalake(new java.io.File(configuration().get("datalake_path")));
		this.terminal = new io.intino.gamification.model.GamificationTerminal(connector);
	}

	public CoreConfiguration configuration() {
		return configuration;
	}

	@Override
	public io.intino.alexandria.core.Box put(Object o) {
		return this;
	}

	public abstract void beforeStart();

	public io.intino.alexandria.core.Box start() {
		if (owner != null) owner.beforeStart();
		beforeStart();
		if (owner != null) owner.startServices();
		startServices();
		if (owner != null) owner.afterStart();
		afterStart();
		return this;
	}

	public abstract void afterStart();

	public abstract void beforeStop();

	public void stop() {
		if (owner != null) owner.beforeStop();
		beforeStop();
		if (owner != null) owner.stopServices();
		stopServices();
		if (owner != null) owner.afterStop();
		afterStop();
	}

    @Override
	public void stopServices() {
		if (connector instanceof io.intino.alexandria.terminal.JmsConnector) ((io.intino.alexandria.terminal.JmsConnector) connector).stop();
	}

	public abstract void afterStop();

    @Override
	public void startServices() {
		initUI();
		initConnector();
		initRestServices();
		initSoapServices();
		initJmxServices();
		initDatalake();
		initTerminal();
		initMessagingServices();
		initSentinels();
		initSlackBots();
		initWorkflow();
	}

	public io.intino.gamification.model.GamificationTerminal terminal() {
		return this.terminal;
	}

	protected io.intino.alexandria.terminal.Connector datahubConnector() {
		return this.connector;
	}

	public io.intino.alexandria.datalake.Datalake datalake() {
		return this.datalake;
	}



	private void initRestServices() {

	}

	private void initSoapServices() {

	}

	private void initMessagingServices() {

	}

	private void initJmxServices() {

	}

	private void initSlackBots() {

	}

	private void initUI() {

	}

	private void initDatalake() {
	}

	private void initConnector() {
		if (connector instanceof io.intino.alexandria.terminal.JmsConnector && ((io.intino.alexandria.terminal.JmsConnector) connector).connection() == null) ((io.intino.alexandria.terminal.JmsConnector) connector).start();
	}

	private void initTerminal() {
		if (this.terminal != null) {
			this.terminal.subscribe((io.intino.gamification.model.GamificationTerminal.GamificationAttackConsumer) e -> new io.intino.gamification.core.box.subscribers.AttackSubscriber((CoreBox) AbstractBox.this).accept(e), "Attack");
			this.terminal.subscribe((io.intino.gamification.model.GamificationTerminal.GamificationHealConsumer) e -> new io.intino.gamification.core.box.subscribers.HealSubscriber((CoreBox) AbstractBox.this).accept(e), "Heal");
			this.terminal.subscribe((io.intino.gamification.model.GamificationTerminal.GamificationMissionConsumer) e -> new io.intino.gamification.core.box.subscribers.MissionSubscriber((CoreBox) AbstractBox.this).accept(e), "Mission");
			this.terminal.subscribe((io.intino.gamification.model.GamificationTerminal.GamificationMissionCompleteConsumer) e -> new io.intino.gamification.core.box.subscribers.MissionCompleteSubscriber((CoreBox) AbstractBox.this).accept(e), "MissionComplete");
			this.terminal.subscribe((io.intino.gamification.model.GamificationTerminal.GamificationMissionFailedConsumer) e -> new io.intino.gamification.core.box.subscribers.MissionFailedSubscriber((CoreBox) AbstractBox.this).accept(e), "MissionFailed");
			this.terminal.subscribe((io.intino.gamification.model.GamificationTerminal.GamificationMissionCancelledConsumer) e -> new io.intino.gamification.core.box.subscribers.MissionCancelledSubscriber((CoreBox) AbstractBox.this).accept(e), "MissionCancelled");
			this.terminal.subscribe((io.intino.gamification.model.GamificationTerminal.GamificationAchievementConsumer) e -> new io.intino.gamification.core.box.subscribers.AchievementSubscriber((CoreBox) AbstractBox.this).accept(e), "Achievement");
		}
	}

	private void initSentinels() {
	}

	private void initWorkflow() {
	}

	private void initJavaLogger() {
		final java.util.logging.Logger Logger = java.util.logging.Logger.getGlobal();
		final ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.INFO);
		handler.setFormatter(new io.intino.alexandria.logger.Formatter());
		Logger.setUseParentHandlers(false);
		Logger.addHandler(handler);
	}

	protected java.net.URL url(String url) {
		try {
			return new java.net.URL(url);
		} catch (java.net.MalformedURLException e) {
			return null;
		}
	}
}