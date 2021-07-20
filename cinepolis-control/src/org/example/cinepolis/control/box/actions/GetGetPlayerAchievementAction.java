package org.example.cinepolis.control.box.actions;

import io.intino.gamification.core.graph.Achievement;
import org.example.cinepolis.control.box.ControlBox;
import io.intino.alexandria.exceptions.*;
import java.time.*;
import java.util.*;


public class GetGetPlayerAchievementAction extends AbstractGetAction<Achievement> {

	public String world;
	public String id;
	public String player;

	@Override
	protected Achievement get() {
		return null;
	}
}