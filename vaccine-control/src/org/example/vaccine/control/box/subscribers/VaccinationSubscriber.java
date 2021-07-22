package org.example.vaccine.control.box.subscribers;

import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.control.box.adapter.CreatePatientAdapter;
import org.example.vaccine.control.box.adapter.UpdatePatientAdapter;
import org.example.vaccine.control.box.graph.mounters.VaccineMounter;
import org.example.vaccine.control.graph.ControlGraph;
import org.example.vaccine.control.graph.Patient;
import org.example.vaccine.datahub.events.vaccines.Vaccination;

public class VaccinationSubscriber implements java.util.function.Consumer<Vaccination> {

	private final ControlBox box;

	public VaccinationSubscriber(ControlBox box) {
		this.box = box;
	}

	private ControlGraph graph() {
		return box.graph();
	}

	@Override
	public void accept(Vaccination event) {
		if(patientDoesNotExists(event.patientName()))
			createPatient(event);
		else
			updatePatient(event);

		new VaccineMounter(box).handle(event);
	}

	private boolean patientDoesNotExists(String patientName) {
		return !graph().contains(patientName, Patient.class);
	}

	private void createPatient(Vaccination event) {
		new CreatePatientAdapter(box).adapt(event);
	}

	private void updatePatient(Vaccination event) {
		new UpdatePatientAdapter(box).adapt(event);
	}
}