package org.example.vaccine.control.box.graph.mounters;

import io.intino.alexandria.event.Event;
import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.control.box.mounters.Mounter;
import org.example.vaccine.control.graph.ControlGraph;
import org.example.vaccine.control.graph.Hospital;
import org.example.vaccine.control.graph.Patient;
import org.example.vaccine.control.graph.Vaccine;
import org.example.vaccine.datahub.events.vaccines.CreateHospital;
import org.example.vaccine.datahub.events.vaccines.CreateVaccine;
import org.example.vaccine.datahub.events.vaccines.Vaccination;
import org.example.vaccine.datahub.events.vaccines.VaccineSupply;

import java.nio.charset.StandardCharsets;

public class VaccineMounter implements Mounter {

	private final ControlBox box;

	public VaccineMounter(ControlBox box) {
		this.box = box;
	}

	private ControlGraph graph() {
		return box.graph();
	}

	public void handle(Vaccination event) {
		Patient patient = graph().get(event.vaccineName(), Patient.class);
		if(patient == null)
			patient = graph().create().patient(event.patientName(), event.patientAge(), event.vaccineName(), 0);
		patient.doseCount(patient.doseCount() + 1);
		patient.save$();
	}

	private void handle(CreateHospital event) {
		Hospital hospital = graph().get(event.name(), Hospital.class);
		if(hospital != null) return;
		hospital = graph().create().hospital(event.name(), event.location());
		hospital.save$();
	}

	private void handle(CreateVaccine event) {
		Vaccine vaccine = graph().get(event.name(), Vaccine.class);
		if(vaccine != null) return;
		vaccine = graph().create().vaccine(event.name(), event.illness(), event.requiredDoseCount(), event.effectiveness());
		vaccine.save$();
	}

	private void handle(VaccineSupply event) {
		Hospital hospital = graph().get(event.hospitalName(), Hospital.class);
		if(hospital == null) return;
		Hospital.Inventory inventory = hospital.inventory(h -> h.name().equals(event.hospitalName()));
		if(inventory == null)
			inventory = hospital.create().inventory(event.vaccineName(), event.units());
		else
			inventory.count(event.units());
		inventory.save$();
		hospital.save$();
	}

	public void handle(Event event) {
		if(event instanceof Vaccination) handle((Vaccination) event);
		else if(event instanceof CreateVaccine) handle((CreateVaccine) event);
		else if(event instanceof VaccineSupply) handle((VaccineSupply) event);
		else if(event instanceof CreateHospital) handle((CreateHospital) event);
	}
}