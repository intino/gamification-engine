package org.example.control.box.subscribers;

import io.intino.gamification.Engine;
import io.intino.gamification.core.box.events.CreateEntity;
import org.example.control.box.ControlBox;
import org.example.control.box.graph.mounters.VaccineMounter;
import org.example.control.graph.ControlGraph;
import org.example.control.graph.Hospital;
import org.example.control.graph.Patient;
import org.example.control.graph.Vaccine;
import org.example.control.util.HospitalRegistry;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

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

	public void accept(org.example.datahub.events.example.Vaccine event) {
		if(!graph().contains(event.hospitalName(), Hospital.class)) createHospitalEntity(event);
		if(!graph().contains(event.patientName(), Patient.class)) createPatientEntity(event);
		if(!graph().contains(event.vaccineName(), Vaccine.class)) createVaccineEntity(event);
		new VaccineMounter(box).handle(event);
	}

	private void createHospitalEntity(org.example.datahub.events.example.Vaccine event) {
		Map<String, String> attributes = new HashMap<>();
		attributes.put("name", event.hospitalName());
		attributes.put("location", HospitalRegistry.locationOf(event.hospitalName()));

		CreateEntity entity = new CreateEntity();
		entity.id(event.hospitalName());
		entity.ss("Example");
		entity.ts(Instant.now());
		entity.type(CreateEntity.Type.Player);
		entity.attributes(attributes);

		// TODO
	}

	private void createPatientEntity(org.example.datahub.events.example.Vaccine event) {
		Map<String, String> attributes = new HashMap<>();
		attributes.put("name", event.patientName());
		attributes.put("doseCount", "1");

		CreateEntity entity = new CreateEntity();
		entity.id(event.patientName());
		entity.ss("Example");
		entity.ts(Instant.now());
		entity.type(CreateEntity.Type.Npc);
		entity.type();
	}

	private void createVaccineEntity(org.example.datahub.events.example.Vaccine event) {


	}
}