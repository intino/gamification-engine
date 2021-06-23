package org.example.smartbrain.control.box.graph.mounters;

import org.example.smartbrain.control.box.ControlBox;
import io.intino.alexandria.event.Event;

import org.example.smartbrain.control.box.mounters.Mounter;
import org.example.smartbrain.control.graph.Task;
import org.example.smartbrain.control.graph.Task.State;
import org.example.smartbrain.datahub.events.smartbrain.TaskAssign;

public class TaskMounter implements Mounter {

	private final ControlBox box;

	public TaskMounter(ControlBox box) {
		this.box = box;
	}

	public void handle(TaskAssign event) {
		Task task = box.graph().create().task(event.taskId(), event.taskName(), event.patientId());
		task.description(event.description());
		task.startTime(event.ts());
		task.durationMinutes(event.durationMinutes().intValue());
		task.state(State.Pending);
		task.save$();
	}

	public void handle(org.example.smartbrain.datahub.events.smartbrain.TaskComplete event) {
		Task task = box.graph().taskList(t -> t.taskId().equals(event.taskId())).findAny().orElse(null);
		if(task == null) return;
		task.state(event.ts().isAfter(task.startTime().plusSeconds(task.durationMinutes())) ? State.Failed : State.Complete);
		task.endTime(event.ts());
	}

	public void handle(Event event) {
		if	(event instanceof TaskAssign) handle((org.example.smartbrain.datahub.events.smartbrain.TaskAssign) event);
		if	(event instanceof org.example.smartbrain.datahub.events.smartbrain.TaskComplete) handle((org.example.smartbrain.datahub.events.smartbrain.TaskComplete) event);
	}
}