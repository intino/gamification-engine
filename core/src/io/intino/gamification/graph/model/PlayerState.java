package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.Fact;

import java.util.List;
import java.util.stream.Stream;

import static io.intino.gamification.util.data.Progress.State.InProgress;

public final class PlayerState extends Node {

    private NodeCollection<MissionAssignment> activeMissions;
    private NodeCollection<MissionAssignment> finishedMissions;

    PlayerState(String id) {
        super(id);
    }

    @Override
    void onInit() {
        if(activeMissions == null) activeMissions = new NodeCollection<>();
        activeMissions.init(this, MissionAssignment.class);

        if(finishedMissions == null) finishedMissions = new NodeCollection<>();
        finishedMissions.init(this, MissionAssignment.class);
    }

    public void assignMission(MissionAssignment assignment) {
        if(assignment.state() == InProgress) {
            activeMissions.add(assignment);
        } else {
            finishedMissions.add(assignment);
        }
    }

    public void updateMission(MissionAssignment assignment) {
        if(assignment.state() != InProgress) return;
        assignment.update();
        if(assignment.state() != InProgress) {
            activeMissions.remove(assignment);
            finishedMissions.add(assignment);
        }
    }

    public void completeMission(MissionAssignment assignment) {
        if(assignment.state() != InProgress) return;
        assignment.complete();
        activeMissions.remove(assignment);
        finishedMissions.add(assignment);
    }

    public void failMission(MissionAssignment assignment) {
        if(assignment.state() != InProgress) return;
        assignment.fail();
        activeMissions.remove(assignment);
        finishedMissions.add(assignment);
    }

    // Facts from finished matches
    public Stream<Fact> facts() {
        if(parent() == null) return Stream.empty();
        return season().rounds().stream()
                .filter(round -> round.state() == Round.State.Finished)
                .flatMap(this::factsOfMatch);
    }

    // All facts, including those in the current round
    public Stream<Fact> rawFacts() {
        if(parent() == null) return Stream.empty();
        return season().rounds().stream()
                .flatMap(this::factsOfMatch);
    }

    private Stream<Fact> factsOfMatch(Round round) {
        Match match = round.matches().addIfNotExists(id(), () -> new Match(id()));
        return match.facts().stream();
    }

    // Score from finished matches
    public int score() {
        if(parent() == null) return 0;

        int score = 0;

        for(Round round : season().rounds()) {
            if(round.state() != Round.State.Finished) continue;
            Match match = round.matches().find(id());
            if(match != null) score += Math.max(match.score(), 0);
        }

        return Math.max(score, 0);
    }

    // Score, including points gained during the current round
    public int rawScore() {
        if(parent() == null) return 0;

        int score = 0;

        for(Round round : season().rounds()) {
            Match match = round.matches().find(id());
            if(match != null) score += Math.max(match.score(), 0);
        }

        return Math.max(score, 0);
    }

    public List<MissionAssignment> activeMissions() {
        return activeMissions.list();
    }

    public List<MissionAssignment> finishedMissions() {
        return finishedMissions.list();
    }

    private Competition competition() {
        return season().competition();
    }

    public Season season() {
        return parent();
    }

    @Override
    public String toString() {
        return "PlayerState{" +
                "missionAssignments=" + activeMissions +
                '}';
    }
}
