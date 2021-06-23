package org.example.smartbrain.control.box;

import io.intino.gamification.Engine;
import io.intino.gamification.core.box.utils.TimeUtils;
import io.intino.magritte.framework.Graph;
import io.intino.magritte.framework.stores.FileSystemStore;
import io.intino.magritte.io.Stash;
import org.example.smartbrain.control.graph.ControlGraph;

import java.io.File;

public class Main {

	private static final String Stashes = "Control";
	private static final String[] StartUpStashes = {Stashes};

	public static void main(String[] args) {
		ControlBox box = new ControlBox(args);
		Graph graph = new Graph(store(box.datamart().root())).loadStashes(false, StartUpStashes);
		box.put(new ControlGraph(graph));

		Engine engine = new Engine(box.configuration());
		box.put(engine);

		engine.launch(() -> onInit(box, engine));
	}

	private static void onInit(ControlBox box, Engine engine) {
		box.start();
		Runtime.getRuntime().addShutdownHook(new Thread(box::stop));
		engine.configuration().gameLoopConfigurator.schedule(1, TimeUtils.Scale.Day);
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