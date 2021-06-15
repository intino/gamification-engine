package org.example.vaccine.control.box.subscribers;

import org.example.vaccine.control.box.ControlBox;
import org.example.vaccine.control.box.adapter.CreateHospitalAdapter;
import org.example.vaccine.control.graph.ControlGraph;
import org.example.vaccine.control.graph.Hospital;
import org.example.vaccine.datahub.events.vaccines.CreateHospital;

public class CreateHospitalSubscriber implements java.util.function.Consumer<CreateHospital> {

	private final ControlBox box;

	public CreateHospitalSubscriber(ControlBox box) {
		this.box = box;
	}

	private ControlGraph graph() {
		return box.graph();
	}

	public void accept(CreateHospital event) {
		createHospitalPlayer(event);
		box.mounter().handle(event);
	}

	private void createHospitalPlayer(CreateHospital event) {
		if(hospitalPlayerExists(event.name())) return;
		new CreateHospitalAdapter(box).adapt(event);
	}

	private boolean hospitalPlayerExists(String name) {
		return graph().contains(name, Hospital.class);
	}
}