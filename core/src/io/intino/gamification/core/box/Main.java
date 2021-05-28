package io.intino.gamification.core.box;

import io.intino.alexandria.core.Box;
import io.intino.alexandria.logger4j.Logger;
import io.intino.magritte.framework.Graph;
import io.intino.magritte.framework.stores.FileSystemStore;
import io.intino.magritte.io.Stash;
import org.apache.log4j.Level;

import java.io.File;

public class Main {

	private static final String Gamification = "Gamification";
	private static final String[] StartUpStashes = {Gamification, "Player", "Npc", "Mission", "Achievement"};

	public static void main(String[] args) {
		Logger.init(Level.ERROR);
		CoreBox box = new CoreBox(args);
		//Graph graph = new Graph(store(box.datamart().root())).loadStashes(false, StartUpStashes);
		//box.put(graph);
		box.start();
		Runtime.getRuntime().addShutdownHook(new Thread(box::stop));
	}

	private static FileSystemStore store(File datamartFolder) {
		return new FileSystemStore(datamartFolder) {

			@Override
			public Stash stashFrom(String path) {
				Stash stash = super.stashFrom(path);
				if (stash != null && stash.language == null) stash.language = Gamification;
				return stash;
			}

			@Override
			public void writeStash(Stash stash, String path) {
				stash.language = stash.language == null || stash.language.isEmpty() ? Gamification : stash.language;
				super.writeStash(stash, path);
			}
		};
	}
}