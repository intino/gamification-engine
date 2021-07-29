package io.intino.gamification.core.model;

import java.util.List;

public class Item extends Component {

    private final String worldId;
    private final boolean enabled;
    private final double health;
    private final List<String> groups;
    private final String owner;

    public Item(io.intino.gamification.core.graph.Item item) {
        super(item.id());
        this.worldId = item.worldId();
        this.enabled = item.enabled();
        this.health = item.health();
        this.groups = item.groups();
        this.owner = item.owner();
    }

    public String worldId() {
        return worldId;
    }

    public boolean enabled() {
        return enabled;
    }

    public double health() {
        return health;
    }

    public List<String> groups() {
        return groups;
    }

    public String owner() {
        return owner;
    }
}