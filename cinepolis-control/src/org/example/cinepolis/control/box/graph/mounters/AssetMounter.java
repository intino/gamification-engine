package org.example.cinepolis.control.box.graph.mounters;

import org.example.cinepolis.control.box.ControlBox;
import io.intino.alexandria.event.Event;

import org.example.cinepolis.control.box.mounters.Mounter;

public class AssetMounter implements Mounter {
	private final ControlBox box;

	public AssetMounter(ControlBox box) {
		this.box = box;
	}

	public void handle(org.example.cinepolis.datahub.events.cinepolis.RegisterAsset event) {

	}

	public void handle(org.example.cinepolis.datahub.events.cinepolis.DeregisterAsset event) {

	}

	public void handle(Event event) {
		if	(event instanceof org.example.cinepolis.datahub.events.cinepolis.RegisterAsset) handle((org.example.cinepolis.datahub.events.cinepolis.RegisterAsset) event);

		if	(event instanceof org.example.cinepolis.datahub.events.cinepolis.DeregisterAsset) handle((org.example.cinepolis.datahub.events.cinepolis.DeregisterAsset) event);
	}
}