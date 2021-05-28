package io.intino.gamification.model;

import io.intino.alexandria.core.Box;
import io.intino.alexandria.logger.Logger;
import io.intino.datahub.box.DataHubBox;
import io.intino.datahub.box.DataHubConfiguration;
import io.intino.datahub.graph.NessGraph;
import io.intino.magritte.framework.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {

	private static final String[] NessStashes = {"Solution"};

	public static void main(String[] args) {

		//"home", "temp"
		//"datalake_directory", "temp/datalake"
		//"broker_directory", "temp/datahub/broker"
		//"broker_port", "63000"
		//"broker_secondary_port", "1883"
		//"backup_directory", "temp/backup"

		DataHubConfiguration configuration = new DataHubConfiguration(args);
		NessGraph nessGraph = new Graph().loadStashes(NessStashes).as(NessGraph.class);
		loadUsers(configuration.home(), nessGraph);
		Box box = new DataHubBox(args).put(nessGraph.core$()).start();
		Runtime.getRuntime().addShutdownHook(new Thread(box::stop));
	}

	private static void loadUsers(File workspace, NessGraph nessGraph) {
		try {
			File file = new File(workspace, "users.bin");
			if (!file.exists()) return;
			nessGraph.broker().clear().user(u -> true);
			String[] users = new String(Files.readAllBytes(file.toPath())).split("\n");
			for (String user : users) nessGraph.broker().create().user(user.split("::")[0], user.split("::")[1]);
		} catch (IOException e) {
			Logger.error(e);
		}
	}
}