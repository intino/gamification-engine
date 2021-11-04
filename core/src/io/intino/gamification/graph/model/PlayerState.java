package io.intino.gamification.graph.model;

import io.intino.gamification.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class PlayerState extends CompetitionNode {

    private final List<MissionAssignment> missionAssignments = new ArrayList<>();
    private final List<Fact<Integer>> facts = new ArrayList<>();

    PlayerState(String id) {
        super(id);
    }

    public void assignMission(MissionAssignment missionAssignment) {
        if(missionAssignment == null) throw new NullPointerException("MissionAssignment cannot be null");

        Mission mission = competition().missions().find(missionAssignment.missionId());
        if(mission == null) {
            NoSuchElementException e = new NoSuchElementException("Mission " + missionAssignment.missionId() + " not exists");
            Log.error(e.getMessage(), e);
            throw e;
        }

        //TODO: Asignar competicion, season, round y player a missionAssignment

        missionAssignments.add(missionAssignment);
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

    List<MissionAssignment> missionAssignments() {
        return missionAssignments;
    }

    List<Fact<Integer>> facts() {
        return facts;
    }
}
