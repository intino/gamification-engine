package io.intino.gamification.graph.model;

import io.intino.gamification.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static io.intino.gamification.util.data.Progress.State.InProgress;

public class PlayerState extends CompetitionNode {

    private NodeCollection<MissionAssignment> missionAssignments;
    private final List<Fact<Integer>> facts = new ArrayList<>();

    PlayerState(String id) {
        super(id);
    }

    @Override
    void init() {
        this.missionAssignments = new NodeCollection<>(competitionId());
    }

    public void assignMission(MissionAssignment missionAssignment) {
        if(missionAssignment == null) throw new NullPointerException("MissionAssignment cannot be null");

        Mission mission = competition().missions().find(missionAssignment.missionId());
        if(mission == null) {
            NoSuchElementException e = new NoSuchElementException("Mission " + missionAssignment.missionId() + " not exists");
            Log.error(e.getMessage(), e);
            throw e;
        }

        //TODO: Asignar competicion, season, round y player a missionAssignment (los params que hagan falta)

        missionAssignments.add(missionAssignment);
    }

    void failMission(String missionId) {
        missionAssignments.stream()
                .filter(ma -> ma.missionId().equals(missionId))
                .forEach(MissionAssignment::fail);
    }

    void completeMission(String missionId) {
        missionAssignments.stream()
                .filter(ma -> ma.missionId().equals(missionId))
                .forEach(MissionAssignment::complete);
    }

    void cancelMission(String missionId) {
        missionAssignments.removeIf(ma ->
                ma.missionId().equals(missionId) && ma.progress().state() == InProgress
        );
    }

    PlayerState copy() {
        PlayerState ps = new PlayerState(id());
        for (MissionAssignment missionAssignment : missionAssignments) {
            ps.missionAssignments.add(missionAssignment.copy());
        }
        return ps;
    }

    Player player() {
        return competition().players().find(id());
    }

    public final NodeCollection<MissionAssignment> missionAssignments() {
        return missionAssignments;
    }

    List<Fact<Integer>> facts() {
        return facts;
    }
}
