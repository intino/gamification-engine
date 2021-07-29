package io.intino.gamification.core.box;

import io.intino.gamification.GamificationEngine;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) {
		Map<String, String> argsMap = Arrays.stream(args)
				.collect(Collectors.toMap(a -> a.split("=")[0], a -> a.split("=")[1]));
		GamificationEngine engine = new GamificationEngine(argsMap);
		engine.launch();
	}
}