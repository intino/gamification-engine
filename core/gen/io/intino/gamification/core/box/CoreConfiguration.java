package io.intino.gamification.core.box;

import java.util.Map;
import java.util.HashMap;

public class CoreConfiguration extends io.intino.alexandria.core.BoxConfiguration {

	public CoreConfiguration(String[] args) {
		super(args);
	}

	public String datahubUrl() {
		return get("datahub_url");
	}

	public String datahubUser() {
		return get("datahub_user");
	}

	public String datahubPassword() {
		return get("datahub_password");
	}

	public String datahubClientid() {
		return get("datahub_clientId");
	}

	public String datahubOutboxDirectory() {
		return get("datahub_outbox_directory");
	}

	public String datalakePath() {
		return get("datalake_path");
	}

	public java.io.File home() {
		return new java.io.File(args.getOrDefault("home", System.getProperty("user.home")));
	}
}