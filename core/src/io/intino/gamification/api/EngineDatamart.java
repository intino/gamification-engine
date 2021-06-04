package io.intino.gamification.api;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.graph.*;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class EngineDatamart {

    private final CoreBox box;

    public EngineDatamart(CoreBox box) {
        this.box = box;
    }

    public List<Entity> entities() {
        return box.graph().entityList();
    }

    public Entity entity(String name) {
        return entities().stream().filter(e -> e.id().equals(name)).findFirst().orElse(null);
    }

    public List<World> worlds() {
        return box.graph().worldList();
    }

    public World world(String id) {
        return worlds().stream().filter(w -> w.id().equals(id)).findFirst().orElse(null);
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
}
