package io.intino.gamification.core.box;

import io.intino.gamification.GamificationEngine;

public class Main {

	public static void main(String[] args) {
		GamificationEngine engine = new GamificationEngine(new CoreConfiguration(args));
		engine.launch();
	}
}