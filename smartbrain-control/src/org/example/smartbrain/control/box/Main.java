package org.example.smartbrain.control.box;

import io.intino.alexandria.core.Box;

public class Main {
	public static void main(String[] args) {
		ControlBox box = new ControlBox(args);
		box.start();
		Runtime.getRuntime().addShutdownHook(new Thread(box::stop));
	}
}