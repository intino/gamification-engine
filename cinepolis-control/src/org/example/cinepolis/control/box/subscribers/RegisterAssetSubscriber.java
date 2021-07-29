package org.example.cinepolis.control.box.subscribers;

import org.example.cinepolis.control.box.ControlBox;

public class RegisterAssetSubscriber implements java.util.function.BiConsumer<org.example.cinepolis.datahub.events.cinepolis.RegisterAsset, String> {
	private final ControlBox box;

	public RegisterAssetSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.cinepolis.datahub.events.cinepolis.RegisterAsset event, String topic) {
		box.mounter().handle(event);
		box.adapter().adapt(event);
	}
}