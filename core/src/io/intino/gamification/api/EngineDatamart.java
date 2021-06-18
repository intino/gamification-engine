package io.intino.gamification.api;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.graph.*;

import java.util.List;
import java.util.Optional;

public class EngineDatamart {

    private final CoreBox box;

    public EngineDatamart(CoreBox box) {
        this.box = box;
    }

    public void clear() {
        box.graph().clear();
    }

    public World world(String id) {
        return box.graph().worldList(w -> w.id().equals(id)).findFirst().orElse(null);
    }

    public List<World> worlds() {
        return box.graph().worldList();
    }

    public Match match(String worldId) {
        return Optional.ofNullable(world(worldId)).map(AbstractWorld::match).orElse(null);
    }

    public Player player(String worldId, String id) {
        return Optional.ofNullable(world(worldId)).map(w -> box.graph().player(w.players(), id)).orElse(null);
    }

    public Npc npc(String worldId, String id) {
        return Optional.ofNullable(world(worldId)).map(w -> box.graph().npc(w.npcs(), id)).orElse(null);
    }

    public Item item(String worldId, String id) {
        return Optional.ofNullable(world(worldId)).map(w -> box.graph().item(w.items(), id)).orElse(null);
    }

    public Mission mission(String worldId, String id) {
        return Optional.ofNullable(world(worldId))
                .map(AbstractWorld::match)
                .map(m -> box.graph().mission(m.missions(), id))
                .orElse(null);
    }

    public List<Mission> missions(String worldId) {
        return Optional.ofNullable(world(worldId))
                .map(AbstractWorld::match)
                .map(AbstractMatch::missions)
                .orElse(null);
    }

    public Achievement globalAchievement(String worldId, String id) {
        return Optional.ofNullable(world(worldId))
                .map(w -> box.graph().achievement(w.achievements(), id))
                .orElse(null);
    }

    public Achievement localAchievement(String worldId, String id) {
        return Optional.ofNullable(world(worldId))
                .map(AbstractWorld::match)
                .map(m -> box.graph().achievement(m.achievements(), id))
                .orElse(null);
    }

    public List<Achievement> globalAchievements(String worldId) {
        return Optional.ofNullable(world(worldId))
                .map(World::achievements)
                .orElse(null);
    }

    public List<Achievement> localAchievements(String worldId) {
        return Optional.ofNullable(world(worldId))
                .map(AbstractWorld::match)
                .map(AbstractMatch::achievements)
                .orElse(null);
    }
}