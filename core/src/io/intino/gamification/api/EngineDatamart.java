package io.intino.gamification.api;

import io.intino.gamification.api.model.*;
import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.graph.AbstractWorld;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EngineDatamart {

    private final CoreBox box;

    public EngineDatamart(CoreBox box) {
        this.box = box;
    }

    public void clear() {
        box.graph().clear();
    }

    public World world(String id) {
        return new World(box.graph().world(id));
    }

    public List<World> worlds() {
        return box.graph().worldList().stream().map(World::new).collect(Collectors.toList());
    }

    public Match match(String worldId) {
        return Optional.ofNullable(box.graph().world(worldId)).map(w -> new Match(w.match())).orElse(null);
    }

    public List<Match> matches() {
        return box.graph().matchList().stream().map(Match::new).collect(Collectors.toList());
    }

    public Player player(String worldId, String id) {
        return Optional.ofNullable(box.graph().world(worldId)).map(w -> new Player(box.graph().player(w.players(), id))).orElse(null);
    }

    public Npc npc(String worldId, String id) {
        return Optional.ofNullable(box.graph().world(worldId)).map(w -> new Npc(box.graph().npc(w.npcs(), id))).orElse(null);
    }

    public Item item(String worldId, String id) {
        return Optional.ofNullable(box.graph().world(worldId)).map(w -> new Item(box.graph().item(w.items(), id))).orElse(null);
    }

    public Mission mission(String worldId, String id) {
        return Optional.ofNullable(box.graph().world(worldId))
                .map(AbstractWorld::match)
                .map(m -> new Mission(box.graph().mission(m.missions(), id)))
                .orElse(null);
    }

    public List<Mission> missions(String worldId) {
        return Optional.ofNullable(box.graph().world(worldId))
                .map(AbstractWorld::match)
                .map(ma -> ma.missions().stream().map(Mission::new).collect(Collectors.toList()))
                .orElse(null);
    }

    public Achievement globalAchievement(String worldId, String id) {
        return Optional.ofNullable(box.graph().world(worldId))
                .map(w -> new Achievement(box.graph().achievement(w.achievements(), id)))
                .orElse(null);
    }

    public Achievement localAchievement(String worldId, String id) {
        return Optional.ofNullable(box.graph().world(worldId))
                .map(AbstractWorld::match)
                .map(m -> new Achievement(box.graph().achievement(m.achievements(), id)))
                .orElse(null);
    }

    public List<Achievement> globalAchievements(String worldId) {
        return Optional.ofNullable(box.graph().world(worldId))
                .map(w -> w.achievements().stream().map(Achievement::new).collect(Collectors.toList()))
                .orElse(null);
    }

    public List<Achievement> localAchievements(String worldId) {
        return Optional.ofNullable(box.graph().world(worldId))
                .map(AbstractWorld::match)
                .map(m -> m.achievements().stream().map(Achievement::new).collect(Collectors.toList()))
                .orElse(null);
    }
}