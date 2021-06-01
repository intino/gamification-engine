package org.example.control.box.graph.mounters;

import org.example.control.box.ControlBox;
import io.intino.alexandria.event.Event;

import org.example.control.box.mounters.Mounter;
import org.example.control.graph.*;
import org.example.control.util.HospitalRegistry;
import org.example.control.util.VaccineRegistry;

public class VaccineMounter implements Mounter {

	private final ControlBox box;

	public VaccineMounter(ControlBox box) {
		this.box = box;
	}

	private ControlGraph graph() {
		return box.graph();
	}

	public void handle(org.example.datahub.events.example.Vaccine event) {
		treatHospital(event.hospitalName());
		treatPatient(event.patientName(), event.patientAge(), event.vaccineName());
		treatVaccine(event.vaccineName());
	}

	private void treatHospital(String hospitalName) {
		Hospital hospital = graph().get(hospitalName, Hospital.class);
		if(hospital == null) hospital = graph().create().hospital(hospitalName, HospitalRegistry.locationOf(hospitalName));
		hospital.save$();
	}

	private void treatPatient(String patientName, int age, String vaccineName) {
		Patient patient = graph().get(patientName, Patient.class);
		if(patient == null) patient = graph().create().patient(patientName, age, vaccineName, 0);
		patient.doseCount(patient.doseCount() + 1);
		patient.save$();
	}

	private void treatVaccine(String vaccineName) {
		Vaccine vaccine = graph().get(vaccineName, Vaccine.class);
		if(vaccine == null) vaccine = graph().create().vaccine(vaccineName, VaccineRegistry.doseCountOf(vaccineName));
		vaccine.save$();
	}

	public void handle(Event event) {
		if	(event instanceof org.example.datahub.events.example.Vaccine) handle((org.example.datahub.events.example.Vaccine) event);
	}
}