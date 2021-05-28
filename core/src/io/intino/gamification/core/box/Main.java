package io.intino.gamification.core.box;

import io.intino.gamification.Engine;

public class Main {

	public static void main(String[] args) {
		Engine engine = new Engine(new CoreConfiguration(args));
		engine.start();
	}
}