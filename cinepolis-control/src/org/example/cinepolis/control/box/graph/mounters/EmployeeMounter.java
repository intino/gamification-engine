package org.example.cinepolis.control.box.graph.mounters;

import io.intino.alexandria.event.Event;
import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.box.mounters.Mounter;
import org.example.cinepolis.control.graph.Employee;

public class EmployeeMounter implements Mounter {

	private final ControlBox box;

	public EmployeeMounter(ControlBox box) {
		this.box = box;
	}

	public void handle(org.example.cinepolis.datahub.events.cinepolis.HireEmployee event) {
		Employee employee = box.graph().employee(event.id());
		if(employee != null) return;
		box.graph().create("Employees").employee(event.id(), event.name(), event.age(), event.phone(), event.area()).save$();
	}

	public void handle(org.example.cinepolis.datahub.events.cinepolis.DismissEmployee event) {
		Employee employee = box.graph().employee(event.id());
		if(employee == null) return;
		employee.delete$();
	}

	public void handle(Event event) {
		if	(event instanceof org.example.cinepolis.datahub.events.cinepolis.HireEmployee) handle((org.example.cinepolis.datahub.events.cinepolis.HireEmployee) event);

		if	(event instanceof org.example.cinepolis.datahub.events.cinepolis.DismissEmployee) handle((org.example.cinepolis.datahub.events.cinepolis.DismissEmployee) event);
	}
}