package old.api;

import old.core.box.CoreBox;
import old.core.box.utils.TimeUtils;
import old.core.model.*;

import java.time.Instant;
import java.util.ArrayList;
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
        return Optional.ofNullable(box.graph().world(id)).map(World::new).orElse(null);
    }

    public List<World> worlds() {
        return box.graph().worldList().stream().map(World::new).collect(Collectors.toList());
    }

    public Match match(String worldId) {
        return Optional.ofNullable(box.graph().world(worldId)).map(w -> new Match(w.match())).orElse(null);
    }

    public Player player(String worldId, String id) {
        return players(worldId).stream()
                .filter(p -> p.id().equals(id))
                .findFirst().orElse(null);
    }

    public List<Player> players(String worldId) {
        return Optional.ofNullable(world(worldId))
                .map(World::players)
                .orElse(new ArrayList<>());
    }

    public Npc npc(String worldId, String id) {
        return npcs(worldId).stream()
                .filter(n -> n.id().equals(id))
                .findFirst().orElse(null);
    }

    public List<Npc> npcs(String worldId) {
        return Optional.ofNullable(world(worldId))
                .map(World::npcs)
                .orElse(new ArrayList<>());
    }

    public Item item(String worldId, String id) {
        return items(worldId).stream()
                .filter(i -> i.id().equals(id))
                .findFirst().orElse(null);
    }

    public List<Item> items(String worldId) {
        return Optional.ofNullable(world(worldId))
                .map(World::items)
                .orElse(new ArrayList<>());
    }

    public Mission mission(String worldId, String id) {
        return missions(worldId).stream()
                .filter(m -> m.id().equals(id))
                .findFirst().orElse(null);
    }

    public List<Mission> missions(String worldId) {
        return Optional.ofNullable(world(worldId))
                .map(World::match)
                .map(Match::missions)
                .orElse(new ArrayList<>());
    }

    public List<Mission> activeMissions(String worldId) {
        Instant now = TimeUtils.currentInstant();
        return missions(worldId).stream().filter(m -> now.isBefore(m.expiration())).collect(Collectors.toList());
    }

    public Achievement globalAchievement(String worldId, String id) {
        return globalAchievements(worldId).stream()
                .filter(a -> a.id().equals(id))
                .findFirst().orElse(null);
    }

    public Achievement localAchievement(String worldId, String id) {
        return localAchievements(worldId).stream()
                .filter(a -> a.id().equals(id))
                .findFirst().orElse(null);
    }

    public List<Achievement> globalAchievements(String worldId) {
        return Optional.ofNullable(world(worldId))
                .map(World::achievements)
                .orElse(new ArrayList<>());
    }

    public List<Achievement> localAchievements(String worldId) {
        return Optional.ofNullable(world(worldId))
                .map(World::match)
                .map(Match::achievements)
                .orElse(new ArrayList<>());
    }
}