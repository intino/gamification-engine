package org.example.control.box.subscribers;

import io.intino.gamification.api.EngineDatamart;
import io.intino.gamification.core.box.events.CreateEntity;
import org.example.control.box.ControlBox;
import org.example.control.box.graph.mounters.VaccineMounter;
import org.example.control.graph.ControlGraph;
import org.example.control.graph.Hospital;
import org.example.datahub.events.example.CreateHospital;

import static io.intino.gamification.core.box.events.attributes.EntityType.Player;

public class CreateHospitalSubscriber implements java.util.function.Consumer<org.example.datahub.events.example.CreateHospital> {

	private final ControlBox box;

	public CreateHospitalSubscriber(ControlBox box) {
		this.box = box;
	}

	private ControlGraph graph() {
		return box.graph();
	}

	private EngineDatamart game() {
		return box.engine().datamart();
	}

	public void accept(org.example.datahub.events.example.CreateHospital event) {
		createHospitalEntity(event);
		new VaccineMounter(box).handle(event);
	}

	private void createHospitalEntity(CreateHospital event) {
		if(graph().contains(event.name(), Hospital.class)) return;

		if(game().world(event.location()) == null) {
			CreateWorld world = new CreateWorld();
			world.id(event.location());
			box.engine().terminal().feed(world);
		}

		CreateEntity entity = new CreateEntity();
		entity.id(event.name());
		entity.type(Player);

		box.engine().terminal().feed(entity);
	}
}