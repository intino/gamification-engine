package io.tetrabot.graph.model;

import io.tetrabot.graph.TetrabotGraph;
import io.tetrabot.util.Json;

import java.util.function.Predicate;

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

    /**
     * Ends the current round and starts the new season as the current one.
     *
     * @param season the new season
     * @param inheritActiveMissions tells whether the new season should carry on the active missions remaining in the last season or not
     *
     * */
    public void startNewSeason(Season season, boolean inheritActiveMissions) {
        if (season != null) {
            Season lastSeason = currentSeason();
            if(lastSeason != null) finishCurrentSeason();
            seasons.add(season);
            season.begin();
            if(lastSeason != null && inheritActiveMissions) {
                inheritActiveMissions(lastSeason, season);
            }
        }
    }

    private void inheritActiveMissions(Season lastSeason, Season currentSeason) {
        for(PlayerState player : currentSeason.playerStates()) {
            PlayerState lastState = lastSeason.playerStates().find(player.id());
            if(lastState == null) continue;
            for(MissionAssignment assignment : lastState.activeMissions()) {
                player.assignMission(assignment.copy());
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

    public TetrabotGraph graph() {
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

    @Override
    public Competition copy() {
        return new Copy().create();
    }

    public class Copy {

        private Predicate<Season> seasonFilter = s -> true;
        private Predicate<Round> roundFilter = r -> true;

        public Competition create() {
            Competition copy = new Competition(id());
            players().forEach(e -> copy.players().add(e.copy()));
            missions().forEach(e -> copy.missions().add(e.copy()));
            reinforcements().forEach(e -> copy.reinforcements().add(e.copy()));
            fouls().forEach(e -> copy.fouls().add(e.copy()));
            achievements().forEach(e -> copy.achievements().add(e.copy()));
            seasons().stream().filter(seasonFilter).forEach(s -> copy.seasons.add(s.new Copy().roundFilter(roundFilter).create()));
            return copy;
        }

        public Copy seasonFilter(Predicate<Season> seasonFilter) {
            this.seasonFilter = seasonFilter == null ? s -> true : seasonFilter;
            return this;
        }

        public Copy roundFilter(Predicate<Round> roundFilter) {
            this.roundFilter = roundFilter == null ? r -> true : roundFilter;
            return this;
        }
    }
}
