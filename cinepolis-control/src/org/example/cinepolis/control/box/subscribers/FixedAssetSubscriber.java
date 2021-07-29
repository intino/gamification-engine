package org.example.cinepolis.control.box.subscribers;

import org.example.cinepolis.control.box.ControlBox;

public class FixedAssetSubscriber implements java.util.function.BiConsumer<org.example.cinepolis.datahub.events.cinepolis.FixedAsset, String> {
	private final ControlBox box;

	public FixedAssetSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.cinepolis.datahub.events.cinepolis.FixedAsset event, String topic) {
		box.mounter().handle(event);
		box.adapter().adapt(event);
	}
}