package org.example.cinepolis.control.box;

import io.intino.gamification.Engine;
import io.intino.magritte.framework.Graph;
import io.intino.magritte.framework.stores.FileSystemStore;
import io.intino.magritte.io.Stash;
import org.example.cinepolis.control.graph.ControlGraph;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main {

	private static final String Stashes = "Control";
	private static final String[] StartUpStashes = {Stashes, "Employees", "Assets", "Alerts"};

	public static void main(String[] args) {
		ControlBox box = new ControlBox(args);
		Graph graph = new Graph(store(box.datamart().root())).loadStashes(false, StartUpStashes);
		box.put(new ControlGraph(graph));

		Map<String, String> engineConfig = new HashMap<>();
		engineConfig.put("gamification_datamart_path", ".temp/datamarts/gamiex");

		Engine engine = new Engine(engineConfig);
		box.put(engine);

		engine.launch(() -> {
			box.start();
			Runtime.getRuntime().addShutdownHook(new Thread(box::stop));
		});
	}

	private static FileSystemStore store(File datamartFolder) {
		return new FileSystemStore(datamartFolder) {

			@Override
			public Stash stashFrom(String path) {
				Stash stash = super.stashFrom(path);
				if (stash != null && stash.language == null) stash.language = Stashes;
				return stash;
			}

			@Override
			public void writeStash(Stash stash, String path) {
				stash.language = stash.language == null || stash.language.isEmpty() ? Stashes : stash.language;
				super.writeStash(stash, path);
			}
		};
	}
}