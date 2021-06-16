package io.intino.gamification.core.box.logic;

import io.intino.gamification.core.graph.Player;

public interface PlayerLevelMapper {
    int level(Player player, int score);
}
