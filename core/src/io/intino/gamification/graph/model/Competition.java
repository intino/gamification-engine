package io.intino.gamification.graph.model;

import io.intino.gamification.util.serializer.Json;

public final class Competition extends Node {

    private transient NodeCollection<Season> seasons;
    private NodeCollection<Player> players;
    private NodeCollection<Mission> missions;
    private NodeCollection<Reinforcement> reinforcements;
    private NodeCollection<Foul> fouls;
    private NodeCollection<Achievement> achievements;

    public Competition(String id) {
        super(id);
    }

    @Override
    void onInit() {
        seasons = new NodeCollection<>();
        seasons.init(absoluteId(), Season.class);

        players = new NodeCollection<>();
        players.init(absoluteId(), Player.class);

        missions = new NodeCollection<>();
        missions.init(absoluteId(), Mission.class);

        reinforcements = new NodeCollection<>();
        reinforcements.init(absoluteId(), Reinforcement.class);

        fouls = new NodeCollection<>();
        fouls.init(absoluteId(), Foul.class);

        achievements = new NodeCollection<>();
        achievements.init(absoluteId(), Achievement.class);
    }

    public Season currentSeason() {
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
        if(currentSeason != null) currentSeason.end();
    }

    public NodeCollection<Season> seasons() {
        return seasons;
    }

    public NodeCollection<Player> players() {
        return players;
    }

    public NodeCollection<Mission> missions() {
        return missions;
    }

    public NodeCollection<Reinforcement> successes() {
        return reinforcements;
    }

    public NodeCollection<Foul> fouls() {
        return fouls;
    }

    public NodeCollection<Achievement> achievements() {
        return achievements;
    }

    @Override
    public Node parent() {
        return null;
    }

    @Override
    public String toString() {
        return id();
    }

    @Override
    public String toJson() {
        return Json.toJsonPretty(this);
    }
}
