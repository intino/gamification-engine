package org.example.vaccine.control.box;

import old.GamificationEngine;
import io.intino.magritte.framework.Graph;
import io.intino.magritte.framework.stores.FileSystemStore;
import io.intino.magritte.io.Stash;
import org.example.vaccine.control.graph.ControlGraph;

import java.io.File;

public class Main {

	private static final String Stashes = "Control";
	private static final String[] StartUpStashes = {Stashes};

	public static void main(String[] args) {
		ControlBox box = new ControlBox(args);
		Graph graph = new Graph(store(box.datamart().root())).loadStashes(false, StartUpStashes);
		box.put(new ControlGraph(graph));

		GamificationEngine engine = new GamificationEngine(box.configuration());
		box.put(engine);

		engine.launch(() -> onInit(box));
	}

	private static void onInit(ControlBox box) {
		box.start();
		Runtime.getRuntime().addShutdownHook(new Thread(box::stop));
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