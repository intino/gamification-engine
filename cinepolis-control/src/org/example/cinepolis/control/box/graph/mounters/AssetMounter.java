package org.example.cinepolis.control.box.graph.mounters;

import org.example.cinepolis.control.box.ControlBox;
import io.intino.alexandria.event.Event;

import org.example.cinepolis.control.box.mounters.Mounter;
import org.example.cinepolis.control.graph.Asset;

public class AssetMounter implements Mounter {

	private final ControlBox box;

	public AssetMounter(ControlBox box) {
		this.box = box;
	}

	public void handle(org.example.cinepolis.datahub.events.cinepolis.RegisterAsset event) {
		Asset asset = box.graph().asset(event.id());
		if(asset != null) return;
		box.graph().create("Assets").asset(event.id(), event.name());
	}

	public void handle(org.example.cinepolis.datahub.events.cinepolis.DeregisterAsset event) {
		Asset asset = box.graph().asset(event.id());
		if(asset == null) return;

		for (int i = asset.alerts().size() - 1; i >= 0 ; i--) asset.alerts(i).delete$();
		asset.delete$();
	}

	public void handle(Event event) {
		if	(event instanceof org.example.cinepolis.datahub.events.cinepolis.RegisterAsset) handle((org.example.cinepolis.datahub.events.cinepolis.RegisterAsset) event);

		if	(event instanceof org.example.cinepolis.datahub.events.cinepolis.DeregisterAsset) handle((org.example.cinepolis.datahub.events.cinepolis.DeregisterAsset) event);
	}
}