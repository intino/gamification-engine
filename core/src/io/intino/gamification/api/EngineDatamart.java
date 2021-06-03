package io.intino.gamification.api;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.graph.*;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class EngineDatamart {

    private final CoreBox box;
    private final Map<String, Entity> entitiesByName;

    public EngineDatamart(CoreBox box) {
        this.box = box;
        this.entitiesByName = new WeakHashMap<>();
    }

    public List<Entity> entities() {
        return box.graph().entityList();
    }

    public Entity entity(String name) {
        if(entitiesByName.containsKey(name)) return entitiesByName.get(name);
        final Entity entity = entities().stream().filter(e -> e.id().equals(name)).findFirst().orElse(null);
        if(entity != null) entitiesByName.put(name, entity);
        return entity;
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
}
