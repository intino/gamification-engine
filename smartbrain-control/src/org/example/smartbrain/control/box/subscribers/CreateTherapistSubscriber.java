package org.example.smartbrain.control.box.subscribers;

import org.example.smartbrain.control.box.ControlBox;
import org.example.smartbrain.control.box.adapter.CreateTherapistPlayerAdapter;
import org.example.smartbrain.datahub.events.smartbrain.CreateTherapist;

public class CreateTherapistSubscriber implements java.util.function.Consumer<CreateTherapist> {

	private final ControlBox box;

	public CreateTherapistSubscriber(ControlBox box) {
		this.box = box;
	}

	public void accept(org.example.smartbrain.datahub.events.smartbrain.CreateTherapist event) {
		box.mounters().handle(event);
		new CreateTherapistPlayerAdapter(box).adapt(event);
	}
}