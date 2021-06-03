package org.example.control.box.graph.mounters;

import io.intino.alexandria.event.Event;
import org.example.control.box.ControlBox;
import org.example.control.box.mounters.Mounter;
import org.example.control.graph.ControlGraph;
import org.example.control.graph.Hospital;
import org.example.control.graph.Patient;
import org.example.control.graph.Vaccine;
import org.example.datahub.events.example.CreateHospital;
import org.example.datahub.events.example.CreateVaccine;

public class VaccineMounter implements Mounter {

	private final ControlBox box;

	public VaccineMounter(ControlBox box) {
		this.box = box;
	}

	private ControlGraph graph() {
		return box.graph();
	}

	public void handle(org.example.datahub.events.example.Vaccine event) {
		Patient patient = graph().get(event.patientName(), Patient.class);
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
		vaccine = graph().create().vaccine(event.name(), event.illness(), event.requiredDoseCount(), event.effectiveness(), event.price());
		vaccine.save$();
	}

	public void handle(Event event) {
		if(event instanceof org.example.datahub.events.example.Vaccine) handle((org.example.datahub.events.example.Vaccine) event);
		else if(event instanceof CreateVaccine) handle((CreateVaccine) event);
		else if(event instanceof CreateHospital) handle((CreateHospital) event);
	}
}