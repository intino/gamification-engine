package io.intino.gamification.events;

import io.intino.gamification.graph.model.Mission;
import io.intino.gamification.graph.model.MissionAssignment;
import io.intino.gamification.graph.model.Player;

public interface MissionEventListener<T extends GamificationEvent> {

    void invoke(T event, Mission mission, Player player, MissionAssignment missionAssignment);
}
