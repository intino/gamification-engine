package io.intino.gamification.graph.model;

import java.util.Objects;

public class Competition extends Node {

    private final NodeCollection<Season> seasons = new NodeCollection<>();
    private final NodeCollection<Entity> entities = new NodeCollection<>();
    private final NodeCollection<Player> players = new NodeCollection<>();
    private final NodeCollection<Mission> missions = new NodeCollection<>();
    private final NodeCollection<Reinforcement> successes = new NodeCollection<>();
    private final NodeCollection<Foul> fouls = new NodeCollection<>();
    private final NodeCollection<Bonus> bonuses = new NodeCollection<>();
    private final NodeCollection<Achievement> achievements = new NodeCollection<>();
    private final NodeCollection<Record> records = new NodeCollection<>();

    public Competition(String id) {
        super(id);
    }

    @Override
    void init() {
        seasons.init(absoluteId());
        entities.init(absoluteId());
        players.init(absoluteId());
        missions.init(absoluteId());
        successes.init(absoluteId());
        fouls.init(absoluteId());
        bonuses.init(absoluteId());
        achievements.init(absoluteId());
        records.init(absoluteId());
    }

    @Override
    void destroyChildren() {
        seasons.forEach(Node::markAsDestroyed);
        entities.forEach(Node::markAsDestroyed);
        players.forEach(Node::markAsDestroyed);
        missions.forEach(Node::markAsDestroyed);
        successes.forEach(Node::markAsDestroyed);
        fouls.forEach(Node::markAsDestroyed);
        bonuses.forEach(Node::markAsDestroyed);
        achievements.forEach(Node::markAsDestroyed);
        records.init(absoluteId());
    }

    public final Season currentSeason() {
        if(seasons.isEmpty()) return null;
        Season season = seasons.last();
        return season.state() == Season.State.Finished ? null : season;
    }

    public final void startNewSeason(Season season) {
        if (season != null) {
            if(currentSeason() == null) {
                seasons.add(season);
                season.begin();
            }
        }
    }

    public final void finishCurrentSeason() {
        Season currentSeason = currentSeason();
        if(currentSeason != null && currentSeason.isAvailable()) currentSeason.end();
    }

    public final NodeCollection<Season> seasons() {
        return seasons;
    }

    public final NodeCollection<Entity> entities() {
        return entities;
    }

    public final NodeCollection<Player> players() {
        return players;
    }

    public final NodeCollection<Mission> missions() {
        return missions;
    }

    public final NodeCollection<Reinforcement> successes() {
        return successes;
    }

    public final NodeCollection<Foul> fouls() {
        return fouls;
    }

    public final NodeCollection<Achievement> achievements() {
        return achievements;
    }

    @Override
    public final Node parent() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Competition that = (Competition) o;
        return Objects.equals(seasons, that.seasons) && Objects.equals(entities, that.entities) && Objects.equals(players, that.players) && Objects.equals(missions, that.missions) && Objects.equals(successes, that.successes) && Objects.equals(fouls, that.fouls) && Objects.equals(bonuses, that.bonuses) && Objects.equals(achievements, that.achievements) && Objects.equals(records, that.records);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), seasons, entities, players, missions, successes, fouls, bonuses, achievements, records);
    }

    @Override
    public String toString() {
        return id();
    }
}
