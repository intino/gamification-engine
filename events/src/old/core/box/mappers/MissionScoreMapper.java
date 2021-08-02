package old.core.box.mappers;

import old.core.model.attributes.MissionState;
import old.core.graph.Mission;
import old.core.graph.Player;

public interface MissionScoreMapper {
    int score(Player player, Mission mission, MissionState state);
}
