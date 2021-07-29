package io.intino.gamification.core.model;

import java.util.List;
import java.util.stream.Collectors;

public class World extends Component {

    private final Match match;
    private final List<Player> players;
    private final List<Item> items;
    private final List<Npc> npcs;
    private final List<Achievement> achievements;

    public World(io.intino.gamification.core.graph.World world) {
        super(world.id());
        this.match = world.match() != null ? new Match(world.match()) : null;
        this.players = world.players().stream().map(Player::new).collect(Collectors.toList());
        this.items = world.items().stream().map(Item::new).collect(Collectors.toList());
        this.npcs = world.npcs().stream().map(Npc::new).collect(Collectors.toList());
        this.achievements = world.achievements().stream().map(Achievement::new).collect(Collectors.toList());
    }

    public Match match() {
        return match;
    }

    public List<Player> players() {
        return players;
    }

    public List<Item> items() {
        return items;
    }

    public List<Npc> npcs() {
        return npcs;
    }

    public List<Achievement> achievements() {
        return achievements;
    }
}
