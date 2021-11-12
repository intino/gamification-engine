package io.intino.gamification.graph.model;

import java.util.Objects;

public class Competition extends Node {

    private final NodeCollection<Season> seasons = new NodeCollection<>();
    private final NodeCollection<Entity> entities = new NodeCollection<>();
    private final NodeCollection<Player> players = new NodeCollection<>();
    private final NodeCollection<Achievement> achievements = new NodeCollection<>();
    private final NodeCollection<Mission> missions = new NodeCollection<>();
    private final NodeCollection<Success> successes = new NodeCollection<>();
    private final NodeCollection<Foul> fouls = new NodeCollection<>();

    public Competition(String id) {
        super(id);
    }

    @Override
    void init() {
        seasons.init(absoluteId());
        entities.init(absoluteId());
        players.init(absoluteId());
        achievements.init(absoluteId());
        missions.init(absoluteId());
        successes.init(absoluteId());
        fouls.init(absoluteId());
    }

    public final NodeCollection<Season> seasons() {
        return seasons;
    }

    public final Season currentSeason() {
        if(seasons.isEmpty()) return null;
        Season season = seasons.last();
        return season.state() == Season.State.Finished ? null : season;
    }

    public final NodeCollection<Mission> missions() {
        return missions;
    }

    @Override
    void destroyChildren() {
        seasons.forEach(Node::markAsDestroyed);
        entities.forEach(Node::markAsDestroyed);
        players.forEach(Node::markAsDestroyed);
        achievements.forEach(Node::markAsDestroyed);
        missions.forEach(Node::markAsDestroyed);
    }

    @Override
    public Node parent() {
        return null;
    }

    public void startNewSeason(Season season) {
        if (season != null) {
            if(currentSeason() == null) {
                seasons.add(season);
                season.begin();
            }
        }
    }

    public void finishCurrentSeason() {
        Season currentSeason = currentSeason();
        if(currentSeason != null && currentSeason.isAvailable()) currentSeason.end();
    }


    public final NodeCollection<Player> players() {
        return players;
    }

    public NodeCollection<Entity> entities() {
        return entities;
    }

    public NodeCollection<Achievement> achievements() {
        return achievements;
    }

    public NodeCollection<Success> successes() {
        return successes;
    }

    public NodeCollection<Foul> fouls() {
        return fouls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Competition that = (Competition) o;
        return Objects.equals(seasons, that.seasons) && Objects.equals(entities, that.entities) && Objects.equals(players, that.players) && Objects.equals(achievements, that.achievements) && Objects.equals(missions, that.missions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), seasons, entities, players, achievements, missions);
    }

    @Override
    public String toString() {
        return id();
    }
}
