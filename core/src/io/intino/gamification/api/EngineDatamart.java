package io.intino.gamification.api;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.graph.*;

import java.util.List;
import io.intino.gamification.core.graph.World;

import java.util.List;

public class EngineDatamart {

    private final CoreBox box;

    public EngineDatamart(CoreBox box) {
        this.box = box;
    }

    public World world(String id) {
        return box.graph().worldList(w -> w.id().equals(id)).findFirst().orElse(null);
    }

    public List<World> worlds() {
        return box.graph().worldList();
    }

    public Mission mission(String id) {
        return box.graph().mission(id);
    }

    public void clear() {
        box.graph().clear();
    }

    public List<Mission> missions() {
        return box.graph().missionList();
    }

    public List<Achievement> achievements() {
        return box.graph().achievementList();
    }

    public List<Match> matches() {
        return box.graph().matchList();
    }

    public Achievement achievement(String id) {
        return box.graph().achievement(id);
    }

    public Match match(String id) {
        return box.graph().match(id);
    }

    public Item item(String id) {
        return box.graph().item(id);
    }
}
