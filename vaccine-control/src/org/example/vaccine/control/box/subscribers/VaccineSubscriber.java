package org.example.vaccine.control.box.subscribers;

import io.intino.gamification.Engine;
import io.intino.gamification.core.box.events.entity.Action;
import io.intino.gamification.core.box.events.entity.CreateEntity;
import io.intino.gamification.core.box.events.entity.EntityType;
import org.example.cinepolis.control.graph.Patient;
import org.example.datahub.events.example.Vaccine;
import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.control.box.graph.mounters.VaccineMounter;
import org.example.vaccine.control.graph.ControlGraph;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static io.intino.gamification.core.box.events.entity.EntityType.*;

public class VaccineSubscriber implements java.util.function.Consumer<org.example.datahub.events.example.Vaccine> {

	private final ControlBox box;

	public VaccineSubscriber(ControlBox box) {
		this.box = box;
	}

	private ControlGraph graph() {
		return box.graph();
	}

	private Engine engine() {
		return box.engine();
	}

	@Override
	public void accept(org.example.datahub.events.example.Vaccine event) {
		treatPatient(event);
		treatVaccine(event);
		new VaccineMounter(box).handle(event);
	}

	private void treatPatient(Vaccine event) {
		if(!graph().contains(event.patientName(), Patient.class)) createPatientEntity(event);
		else updatePatientEvent(event);
	}

	private void treatVaccine(Vaccine event) {
		if(!graph().contains(event.vaccineName(), Vaccine.class)) createVaccineEntity(event);
	}

	private void createPatientEntity(org.example.datahub.events.example.Vaccine event) {
		Map<String, String> attributes = new HashMap<>();
		attributes.put("name", event.patientName());
		attributes.put("doseCount", "1");
		createEntity(event.patientName(), Npc, attributes);
	}

	private void updatePatientEvent(Vaccine event) {
		final int doseCount = box.engine().datamart().entity(event.patientName()).get("doseCount", Integer::parseInt) + 1;

		Action action = new Action();
		action.ss("Example");
		action.ts(Instant.now());
		action.attribute("doseCount");
		action.entity(event.patientName());
		action.value(String.valueOf(doseCount));

		engine().terminal().feed(action);
	}

	private void createVaccineEntity(org.example.datahub.events.example.Vaccine event) {
		Map<String, String> attributes = new HashMap<>();
		attributes.put("name", event.patientName());
		attributes.put("doseCount", "1");
		createEntity(event.patientName(), Item, attributes);
	}

	private void createEntity(String id, EntityType type, Map<String, String> attributes) {
		CreateEntity entity = new CreateEntity();
		entity.id(id);
		entity.ss("Example");
		entity.ts(Instant.now());
		entity.type(type);
		entity.attributes(attributes);

		engine().terminal().feed(entity);
	}
}