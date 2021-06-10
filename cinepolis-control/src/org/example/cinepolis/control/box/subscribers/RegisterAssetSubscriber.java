package org.example.cinepolis.control.box.subscribers;

import org.example.cinepolis.control.box.ControlBox;

public class RegisterAssetSubscriber implements java.util.function.Consumer<org.example.cinepolis.datahub.events.cinepolis.RegisterAsset> {
	private final ControlBox box;

	public RegisterAssetSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.cinepolis.datahub.events.cinepolis.RegisterAsset event) {
		box.mounter().handle(event);
		box.adapter().adapt(event);
	}
}