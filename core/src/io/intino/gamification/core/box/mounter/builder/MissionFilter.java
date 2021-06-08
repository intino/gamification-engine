package io.intino.gamification.core.box.mounter.builder;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.mission.NewMission;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.graph.Match;
import io.intino.gamification.core.graph.Mission;
import io.intino.gamification.core.graph.Player;
import io.intino.gamification.core.graph.World;

public class MissionFilter extends Filter {

    private World world;
    private final Match match;
    private Mission mission;
    private Player player;

    public MissionFilter(CoreBox box, NewMission event) {
        super(box);
        this.match = box.graph().match(event.match());
        if(match != null) {
            this.mission = box.graph().mission(match.missions(), event.id());
        }
    }

    public MissionFilter(CoreBox box, NewStateMission event) {
        super(box);
        this.match = box.graph().match(event.match());
        if(match != null) {
            this.mission = box.graph().mission(match.missions(), event.id());
            this.world = match.world();
            if(world != null) {
                this.player.graph().player(world.players(), event.player());
            }
        }
    }

    public World world() {
        return world;
    }

    public Match match() {
        return match;
    }

    public Player player() {
        return player;
    }

    public Mission mission() {
        return mission;
    }

    public boolean newMissionCanMount() {
        return match != null && mission == null;
    }

    public boolean newStateMissionCanMount() {
        return world != null && match != null && mission != null && player != null;
    }
}
