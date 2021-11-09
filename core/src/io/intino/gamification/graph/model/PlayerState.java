package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static io.intino.gamification.util.data.Progress.State.InProgress;

public class PlayerState extends Node {

    private final NodeCollection<MissionAssignment> missionAssignments = new NodeCollection<>();

    PlayerState(String id) {
        super(id);
    }

    @Override
    void init() {
        missionAssignments.init(absoluteId());
    }

    // Facts from finished matches
    public Stream<Fact> facts() {
        return parent().rounds().stream()
                .filter(round -> round.state() == Round.State.Finished)
                .flatMap(round -> round.matches().find(id()).facts().stream());
    }

    // All facts, including those in the current round
    public Stream<Fact> rawFacts() {
        return parent().rounds().stream()
                .flatMap(round -> round.matches().find(id()).facts().stream());
    }

    // Score from finished matches
    public int score() {
        return facts().mapToInt(Fact::value).sum();
    }

    // Score, including points gained during the current round
    public int rawScore() {
        return rawFacts().mapToInt(Fact::value).sum();
    }

    public final NodeCollection<MissionAssignment> missionAssignments() {
        return missionAssignments;
    }

    @Override
    void destroyChildren() {
        missionAssignments.forEach(Node::markAsDestroyed);
    }

    public Season season() {
        return parent();
    }

    @Override
    public Season parent() {
        String[] ids = parentIds();
        if(ids == null || ids.length == 0) return null;
        return GamificationGraph.get()
                .competitions().find(ids[0])
                .seasons().find(ids[1]);
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

    void endMissions() {
        missionAssignments.forEach(ma -> {
            if(parent().endWithinThisSeason(ma) && ma.progress().state() == InProgress) {
                ma.update(ma.progress().state());
                ma.progress().fail();
            }
        });
    }

    PlayerState copy() {
        PlayerState ps = new PlayerState(id());
        for (MissionAssignment missionAssignment : missionAssignments) {
            ps.missionAssignments.add(missionAssignment.copy());
        }
        return ps;
    }

    private Competition competition() {
        return parent().parent();
    }
}
