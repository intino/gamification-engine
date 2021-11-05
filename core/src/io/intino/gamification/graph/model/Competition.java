package io.intino.gamification.graph.model;

import java.util.LinkedHashMap;

public class Competition extends Node {

    private final NodeCollection<Season> seasons;
    private final NodeCollection<Entity> entities;
    private final NodeCollection<Player> players;
    private final NodeCollection<Achievement> achievements;
    //private final NodeCollection<Record> records;
    private final NodeCollection<Mission> missions;
    //private final NodeCollection<Bonus> bonus;
    //private final NodeCollection<Fail> fails;
    //private final NodeCollection<Success> successes;

    public Competition(String id) {
        super(id);
        this.seasons = new NodeCollection<>(id, new LinkedHashMap<>());
        this.entities = new NodeCollection<>(id);
        this.players = new NodeCollection<>(id);
        this.achievements = new NodeCollection<>(id);
        //this.records = new NodeCollection<>(id);
        this.missions = new NodeCollection<>(id);
        //this.bonus = new NodeCollection<>(id);
        //this.fails = new NodeCollection<>(id);
        //this.successes = new NodeCollection<>(id);
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

    public final Season currentSeason() {
        if(seasons.isEmpty()) return null;
        Season season = seasons.last();
        return season.state() != Season.State.Finished ? null : season;
    }

    public final NodeCollection<Season> seasons() {
        //TODO Devolver unmodifiable
        return seasons;
    }

    public final NodeCollection<Player> players() {
        return players;
    }

    public final NodeCollection<Mission> missions() {
        return missions;
    }
}
