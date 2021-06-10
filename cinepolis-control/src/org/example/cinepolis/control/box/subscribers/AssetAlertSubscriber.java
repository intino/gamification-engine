package org.example.cinepolis.control.box.subscribers;

import org.example.cinepolis.control.box.ControlBox;

public class AssetAlertSubscriber implements java.util.function.Consumer<org.example.cinepolis.datahub.events.cinepolis.AssetAlert> {
	private final ControlBox box;

	public AssetAlertSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.cinepolis.datahub.events.cinepolis.AssetAlert event) {
		box.mounter().handle(event);
		box.adapter().adapt(event);
	}
}