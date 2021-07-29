package org.example.cinepolis.control.box.subscribers;

import org.example.cinepolis.control.box.ControlBox;

public class DeregisterAssetSubscriber implements java.util.function.BiConsumer<org.example.cinepolis.datahub.events.cinepolis.DeregisterAsset, String> {
	private final ControlBox box;

	public DeregisterAssetSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.cinepolis.datahub.events.cinepolis.DeregisterAsset event, String topic) {
		box.mounter().handle(event);
		box.adapter().adapt(event);
	}
}