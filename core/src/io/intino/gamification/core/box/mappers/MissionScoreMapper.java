package io.intino.gamification.core.box.mappers;

import io.intino.gamification.core.box.events.mission.MissionState;
import io.intino.gamification.core.graph.Mission;
import io.intino.gamification.core.graph.Player;

public interface MissionScoreMapper {
    int score(Player player, Mission mission, MissionState state);
}
