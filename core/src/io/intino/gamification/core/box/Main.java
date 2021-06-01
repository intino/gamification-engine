package io.intino.gamification.core.box;

import io.intino.gamification.core.box.launcher.Launcher;

public class Main {

	public static void main(String[] args) {
		Launcher engine = new Launcher(new CoreConfiguration(args));
		engine.start();
	}
}