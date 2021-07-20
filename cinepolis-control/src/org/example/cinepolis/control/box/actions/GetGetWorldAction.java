package org.example.cinepolis.control.box.actions;

import io.intino.gamification.core.model.World;
import org.example.cinepolis.control.box.ControlBox;
import io.intino.alexandria.exceptions.*;
import java.time.*;
import java.util.*;


public class GetGetWorldAction extends AbstractGetAction<World> {

	public String id;

	protected World get() {
		return box.engine().datamart().world(id);
	}
}