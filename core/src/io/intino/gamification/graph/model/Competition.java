package io.intino.gamification.graph.model;

import java.util.LinkedHashMap;
import java.util.Objects;

public class Competition extends Node {

    private NodeCollection<Season> seasons;
    private NodeCollection<Entity> entities;
    private NodeCollection<Player> players;
    private NodeCollection<Achievement> achievements;
    //private NodeCollection<Record> records;
    private NodeCollection<Mission> missions;
    //private NodeCollection<Bonus> bonus;
    //private NodeCollection<Fail> fails;
    //private NodeCollection<Success> successes;

    public Competition(String id) {
        super(id);
    }

    @Override
    void init() {
        this.seasons = new NodeCollection<>(id(), new LinkedHashMap<>());
        this.entities = new NodeCollection<>(id());
        this.players = new NodeCollection<>(id());
        this.achievements = new NodeCollection<>(id());
        //this.records = new NodeCollection<>(id);
        this.missions = new NodeCollection<>(id());
        //this.bonus = new NodeCollection<>(id);
        //this.fails = new NodeCollection<>(id);
        //this.successes = new NodeCollection<>(id);
    }

    public final Season currentSeason() {
        if(seasons.isEmpty()) return null;
        Season season = seasons.last();
        return season.state() == Season.State.Finished ? null : season;
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

    public final NodeCollection<Season> seasons() {
        //TODO Devolver unmodifiable
        return seasons;
    }

    public final NodeCollection<Player> players() {
        //TODO: Devolver unmodifiable
        return players;
    }

    public final NodeCollection<Mission> missions() {
        //TODO: Devolver unmodifiable
        return missions;
    }

    @Override
    void destroyChildren() {
        seasons.forEach(Node::markAsDestroyed);
        entities.forEach(Node::markAsDestroyed);
        players.forEach(Node::markAsDestroyed);
        achievements.forEach(Node::markAsDestroyed);
        //records.forEach(Node::markAsDestroyed);
        missions.forEach(Node::markAsDestroyed);
        //bonus.forEach(Node::markAsDestroyed);
        //fails.forEach(Node::markAsDestroyed);
        //successes.forEach(Node::markAsDestroyed);
    }

    @Override
    protected Node parent() {
        return null;
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
        return "Competition{" +
                "seasons=" + seasons +
                ", entities=" + entities +
                ", players=" + players +
                ", achievements=" + achievements +
                ", missions=" + missions +
                '}';
    }
}
