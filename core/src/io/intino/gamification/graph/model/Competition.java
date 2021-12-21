package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.serializer.Json;

public final class Competition extends Node {

    private final transient NodeCollection<Season> seasons;
    private final NodeCollection<Player> players;
    private final NodeCollection<Mission> missions;
    private final NodeCollection<Reinforcement> reinforcements;
    private final NodeCollection<Foul> fouls;
    private final NodeCollection<Achievement> achievements;

    public Competition(String id) {
        super(id);

        seasons = new NodeCollection<>();
        seasons.init(this, Season.class);

        players = new NodeCollection<>();
        players.init(this, Player.class);

        missions = new NodeCollection<>();
        missions.init(this, Mission.class);

        reinforcements = new NodeCollection<>();
        reinforcements.init(this, Reinforcement.class);

        fouls = new NodeCollection<>();
        fouls.init(this, Foul.class);

        achievements = new NodeCollection<>();
        achievements.init(this, Achievement.class);
    }

    @Override
    void onInit() {
    }

    public Season currentSeason() {
        if(seasons.isEmpty()) return null;
        Season season = seasons.last();
        return season.state() == Season.State.Finished ? null : season;
    }

    public void startNewSeason(Season season) {
        if (season != null) {
            finishCurrentSeason();
            seasons.add(season);
            season.begin();
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
