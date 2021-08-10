package org.example.cinepolis.control.box.graph.mounters;

import io.intino.alexandria.event.Event;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.box.mounters.Mounter;
import org.example.cinepolis.control.graph.Alert;
import org.example.cinepolis.control.graph.Asset;
import org.example.cinepolis.control.graph.rules.AlertImportance;
import org.example.cinepolis.datahub.events.cinepolis.AssetAlert;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class AlertMounter implements Mounter {

	private final ControlBox box;

	public AlertMounter(ControlBox box) {
		this.box = box;
	}

	public void handle(org.example.cinepolis.datahub.events.cinepolis.AssetAlert event) {

		Asset asset = box.graph().asset(event.asset());
		if(asset == null) return;
		Alert alert = box.graph().create("Alerts").alert(event.id(), getToTime(event.ts(), event.limitHours()), event.description(), getImportanceOf(event.importance()));

		asset.alerts().add(alert);

		asset.save$();
		alert.save$();
	}

	public void handle(org.example.cinepolis.datahub.events.cinepolis.FixedAsset event) {

		/*Asset asset = box.graph().asset(event.asset());
		if(asset == null) return;
		Alert alert = box.graph().alert(asset.alerts(), event.alert());
		if(alert == null) return;

		asset.alerts().remove(alert);

		asset.save$();
		alert.delete$();*/
	}

	public void handle(Event event) {
		if	(event instanceof org.example.cinepolis.datahub.events.cinepolis.AssetAlert) handle((org.example.cinepolis.datahub.events.cinepolis.AssetAlert) event);

		if	(event instanceof org.example.cinepolis.datahub.events.cinepolis.FixedAsset) handle((org.example.cinepolis.datahub.events.cinepolis.FixedAsset) event);
	}

	private Instant getToTime(Instant ts, Integer limitHours) {
		return ts.plus(limitHours, ChronoUnit.HOURS);
	}

	private AlertImportance getImportanceOf(AssetAlert.Importance importance) {
		return AlertImportance.valueOf(importance.name());
	}
}