package io.intino.gamification.model;

import io.intino.datahub.box.DataHubConfiguration;
import io.intino.gamification.Model;

public class Main {

	public static void main(String[] args) {
		Model model = new Model(new DataHubConfiguration(args));
	}
}