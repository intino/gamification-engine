package org.example.control.box.subscribers;

import io.intino.gamification.Engine;
import io.intino.gamification.core.box.events.Action;
import io.intino.gamification.core.box.events.CreateEntity;
import org.example.control.box.ControlBox;
import org.example.control.box.graph.mounters.VaccineMounter;
import org.example.control.graph.ControlGraph;
import org.example.control.graph.Hospital;
import org.example.control.graph.Patient;
import org.example.control.util.HospitalRegistry;
import org.example.datahub.events.example.Vaccine;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static io.intino.gamification.core.box.events.CreateEntity.Type.*;

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
		treatHospital(event);
		treatPatient(event);
		treatVaccine(event);
		new VaccineMounter(box).handle(event);
	}

	private void treatHospital(Vaccine event) {
		if(!graph().contains(event.hospitalName(), Hospital.class)) createHospitalEntity(event);
	}

	private void treatPatient(Vaccine event) {
		if(!graph().contains(event.patientName(), Patient.class)) createPatientEntity(event);
		else updatePatientEvent(event);
	}

	private void treatVaccine(Vaccine event) {
		if(!graph().contains(event.vaccineName(), Vaccine.class)) createVaccineEntity(event);
	}

	private void createHospitalEntity(org.example.datahub.events.example.Vaccine event) {
		Map<String, String> attributes = new HashMap<>();
		attributes.put("name", event.hospitalName());
		attributes.put("location", HospitalRegistry.locationOf(event.hospitalName()));
		createEntity(event.hospitalName(), Player, attributes);
	}

	private void createPatientEntity(org.example.datahub.events.example.Vaccine event) {
		Map<String, String> attributes = new HashMap<>();
		attributes.put("name", event.patientName());
		attributes.put("doseCount", "1");
		createEntity(event.patientName(), Npc, attributes);
	}

	private void updatePatientEvent(Vaccine event) {
		Action action = new Action();
		action.ss("Example");
		action.ts(Instant.now());
		action.destEntity(event.patientName());
		action.destEntityAttribute("name");
		action.operationType(Action.OperationType.Sum);
		action.value("1");

		engine().terminal().feed(action);
	}

	private void createVaccineEntity(org.example.datahub.events.example.Vaccine event) {
		Map<String, String> attributes = new HashMap<>();
		attributes.put("name", event.patientName());
		attributes.put("doseCount", "1");
		createEntity(event.patientName(), Item, attributes);
	}

	private void createEntity(String id, CreateEntity.Type type, Map<String, String> attributes) {
		CreateEntity entity = new CreateEntity();
		entity.id(id);
		entity.ss("Example");
		entity.ts(Instant.now());
		entity.type(type);
		entity.attributes(attributes);

		engine().terminal().feed(entity);
	}
}