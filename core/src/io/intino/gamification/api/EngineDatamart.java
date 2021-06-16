package io.intino.gamification.api;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.graph.Achievement;
import io.intino.gamification.core.graph.Mission;
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

    public Achievement achievement(String id) {
        return box.graph().achievement(id);
    }

    /*



    public List<Entity> entities() {
        return box.graph().entityList();
    }

    public Entity entity(String name) {
        return entities().stream().filter(e -> e.id().equals(name)).findFirst().orElse(null);
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

    public Mission mission(String id) {
        return box.graph().mission(id);
    }

    public Item item(String id) {
        return box.graph().item(id);
    }*/
}
