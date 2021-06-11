package io.intino.gamification.core.box.mounter.builder;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.mission.NewMission;
import io.intino.gamification.core.box.events.mission.NewStateMission;
import io.intino.gamification.core.graph.Match;
import io.intino.gamification.core.graph.Mission;
import io.intino.gamification.core.graph.Player;
import io.intino.gamification.core.graph.World;

public class MissionFilter extends Filter {

    private final World world;
    private Match match;
    private Mission mission;
    private Player player;

    public MissionFilter(CoreBox box, NewMission event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.match = world.match();
            if(match != null) {
                this.mission = box.graph().mission(match.missions(), event.id());
            }
        }
    }

    public MissionFilter(CoreBox box, NewStateMission event) {
        super(box);
        this.world = box.graph().world(event.world());
        if(world != null) {
            this.match = world.match();
            if(match != null) {
                this.mission = box.graph().mission(match.missions(), event.id());
                if(mission.players().isEmpty() || mission.players().contains(event.player())) {
                    this.player = box.graph().player(world.players(), event.player());
                }
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
        return world != null && match != null && mission == null;
    }

    public boolean newStateMissionCanMount() {
        return world != null && match != null && mission != null && player != null;
    }
}
