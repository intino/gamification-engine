package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
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
        if(seasons == null) seasons = new NodeCollection<>();
        seasons.init(this, Season.class);

        if(players == null) players = new NodeCollection<>();
        players.init(this, Player.class);

        if(missions == null) missions = new NodeCollection<>();
        missions.init(this, Mission.class);

        if(reinforcements == null) reinforcements = new NodeCollection<>();
        reinforcements.init(this, Reinforcement.class);

        if(fouls == null) fouls = new NodeCollection<>();
        fouls.init(this, Foul.class);

        if(achievements == null) achievements = new NodeCollection<>();
        achievements.init(this, Achievement.class);
    }

    public Season currentSeason() {
        if(seasons.isEmpty()) return null;
        Season season = seasons.last();
        return season.state() == Season.State.Finished ? null : season;
    }

    public void startNewSeason(Season season) {
        if (season != null) {
            if(currentSeason() == null) {
                finishCurrentSeason();
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

    public NodeCollection<Reinforcement> reinforcements() {
        return reinforcements;
    }

    public NodeCollection<Foul> fouls() {
        return fouls;
    }

    public NodeCollection<Achievement> achievements() {
        return achievements;
    }

    public GamificationGraph graph() {
        return parent();
    }

    @Override
    public String toString() {
        return "Competition{" +
                "id=" + id() +
                ", currentSeason=" + currentSeason() +
                ", seasons=" + seasons.size() +
                ", players=" + players.size() +
                ", missions=" + missions.size() +
                ", reinforcements=" + reinforcements.size() +
                ", fouls=" + fouls.size() +
                ", achievements=" + achievements.size() +
                '}';
    }

    @Override
    public String toJson() {
        return Json.toJsonPretty(this);
    }
}
