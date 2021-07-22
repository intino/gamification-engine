package org.example.cinepolis.control.box.actions;

import io.intino.gamification.core.model.Match;
import io.intino.gamification.core.model.Mission;
import org.example.cinepolis.control.box.ControlBox;
import io.intino.alexandria.exceptions.*;
import org.example.cinepolis.control.gamification.GamificationConfig;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class DatamartViewerAction {

	public ControlBox box;

	public void execute() {
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("MISIONES ACTIVAS --------------------------------------");
		box.engine().datamart().activeMissions(GamificationConfig.WorldId)
				.forEach(am -> System.out.println(getLineOf(am)));
		System.out.println("ESTADO DE LOS JUGADORES -------------------------------");
		System.out.println("-------------------------------------------------------");
		box.engine().datamart().match(GamificationConfig.WorldId).playersState()
				.forEach(ps -> System.out.println(getLineOf(ps)));
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println();
	}

	private String getLineOf(Mission mission) {
		return mission.description() + "\t" + mission.expiration().toString();
	}

	private String getLineOf(Match.PlayerState playerState) {
		return playerState.playerId() + "\t" + playerState.score() + "\t" + getLineOf(playerState.missionsState());
	}

	private String getLineOf(List<Match.PlayerState.MissionState> missionsStates) {
		return Arrays.toString(
				missionsStates.stream()
						.map(ms -> "{" + box.engine().datamart().mission(GamificationConfig.WorldId, ms.missionId()).description() + " - " + ms.state().toString() + "}")
						.collect(Collectors.toList()).toArray(String[]::new)
		);
	}
}