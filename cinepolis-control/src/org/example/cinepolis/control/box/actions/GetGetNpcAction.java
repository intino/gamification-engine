package org.example.cinepolis.control.box.actions;

import io.intino.gamification.core.model.Npc;
import io.intino.gamification.core.model.Player;
import org.example.cinepolis.control.box.ControlBox;
import io.intino.alexandria.exceptions.*;
import java.time.*;
import java.util.*;


public class GetGetNpcAction extends AbstractGetAction<Npc> {

	public String world;
	public String id;

	@Override
	protected Npc get() {
		return box.engine().datamart().npc(world, id);
	}
}