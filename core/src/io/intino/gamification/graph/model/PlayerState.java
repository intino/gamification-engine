package io.intino.gamification.graph.model;

import io.intino.gamification.util.Log;
import io.intino.gamification.util.time.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static io.intino.gamification.util.data.Progress.State.InProgress;

public class PlayerState extends CompetitionNode {

    private final Season season;
    private NodeCollection<MissionAssignment> missionAssignments;
    private final List<Fact<Integer>> facts = new ArrayList<>();

    PlayerState(String id, Season season) {
        super(id);
        this.season = season;
    }

    @Override
    void init() {
        this.missionAssignments = new NodeCollection<>(competitionId());
    }

    public void assignMission(MissionAssignment missionAssignment) {
        if(missionAssignment == null) throw new NullPointerException("MissionAssignment cannot be null");

        Mission mission = competition().missions().find(missionAssignment.id());
        if(mission == null) {
            NoSuchElementException e = new NoSuchElementException("Mission " + missionAssignment.id() + " not exists");
            Log.error(e.getMessage(), e);
            throw e;
        }

        //TODO: Asignar competicion, season, round y player a missionAssignment (los params que hagan falta)
        missionAssignment.playerState(season.playerStates().find(id()));

        missionAssignments.add(missionAssignment);
    }

    void failMission(String missionId) {
        missionAssignments.stream()
                .filter(ma -> ma.id().equals(missionId))
                .forEach(MissionAssignment::fail);
    }

    void completeMission(String missionId) {
        missionAssignments.stream()
                .filter(ma -> ma.id().equals(missionId))
                .forEach(MissionAssignment::complete);
    }

    void cancelMission(String missionId) {
        missionAssignments.removeIf(ma ->
                ma.id().equals(missionId) && ma.progress().state() == InProgress
        );
    }

    public final void addFactOf(MissionAssignment assignment) {
        Fact<Integer> fact = new Fact<>(TimeUtils.currentInstant(), Fact.Type.Mission, assignment.id(), assignment.score());
        Round round = season.currentRound();
        if(round == null) return;
        round.matches().computeIfAbsent(id(), k -> new Round.Match(id())).addFact(fact);
    }

    public void sealFacts(List<Fact<Integer>> facts) {
        this.facts.addAll(facts);
    }

    PlayerState copy() {
        PlayerState ps = new PlayerState(id(), season);
        for (MissionAssignment missionAssignment : missionAssignments) {
            ps.missionAssignments.add(missionAssignment.copy());
        }
        return ps;
    }

    Player player() {
        return competition().players().find(id());
    }

    public final Season season() {
        return season;
    }

    public final NodeCollection<MissionAssignment> missionAssignments() {
        return missionAssignments;
    }

    List<Fact<Integer>> facts() {
        return facts;
    }
}
