package org.example.cinepolis.control.box.graph.mounters;

import org.example.cinepolis.control.box.ControlBox;
import io.intino.alexandria.event.Event;

import org.example.cinepolis.control.box.mounters.Mounter;

public class AlertMounter implements Mounter {
	private final ControlBox box;

	public AlertMounter(ControlBox box) {
		this.box = box;
	}

	public void handle(org.example.cinepolis.datahub.events.cinepolis.AssetAlert event) {

	}

	public void handle(org.example.cinepolis.datahub.events.cinepolis.FixedAsset event) {

	}

	public void handle(Event event) {
		if	(event instanceof org.example.cinepolis.datahub.events.cinepolis.AssetAlert) handle((org.example.cinepolis.datahub.events.cinepolis.AssetAlert) event);

		if	(event instanceof org.example.cinepolis.datahub.events.cinepolis.FixedAsset) handle((org.example.cinepolis.datahub.events.cinepolis.FixedAsset) event);
	}
}