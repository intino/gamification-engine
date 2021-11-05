package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static io.intino.gamification.util.data.Progress.State.InProgress;

public class PlayerState extends Node {

    private NodeCollection<MissionAssignment> missionAssignments;
    private final List<Fact> facts = new ArrayList<>();

    PlayerState(String id) {
        super(id);
    }

    @Override
    void init() {
        this.missionAssignments = new NodeCollection<>(absoluteId());
    }

    public final NodeCollection<MissionAssignment> missionAssignments() {
        return missionAssignments;
    }

    @Override
    void destroyChildren() {
        missionAssignments.forEach(Node::markAsDestroyed);
    }

    @Override
    protected Season parent() {
        String[] ids = parentIds();
        return GamificationGraph.get()
                .competitions().find(ids[0])
                .seasons().find(ids[1]);
    }

    /*public void assignMission(MissionAssignment missionAssignment) {
        if(missionAssignment == null) throw new NullPointerException("MissionAssignment cannot be null");

        Mission mission = competition().missions().find(missionAssignment.id());
        if(mission == null) {
            NoSuchElementException e = new NoSuchElementException("Mission " + missionAssignment.id() + " not exists");
            Log.error(e.getMessage(), e);
            throw e;
        }

        //TODO: Asignar competicion, season, round y player a missionAssignment (los params que hagan falta)

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

    public final void addFact(Fact fact) {
        Round round = season.currentRound();
        if(round == null) return;
        round.matches().computeIfAbsent(id(), k -> new Round.Match(id())).addFact(fact);
    }

    public void sealFacts(List<Fact> facts) {
        this.facts.addAll(facts);
    }

    void clearFacts() {
        this.facts.clear();
    }

    void endMissions() {
        missionAssignments.forEach(ma -> {
            if(parent().endWithinThisSeason(ma) && ma.progress().state() == InProgress) {
                ma.update(ma.progress().state());
                ma.progress().fail();
            }
        });
    }

    PlayerState copy() {
        PlayerState ps = new PlayerState(id(), season);
        for (MissionAssignment missionAssignment : missionAssignments) {
            ps.missionAssignments.add(missionAssignment.copy());
        }
        return ps;
    }

    private Competition competition() {
        return parent().parent();
    }

    public final Season season() {
        return season;
    }

    public final List<Fact> facts() {
        return Collections.unmodifiableList(facts);
    }

    */
}
