package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.Fact;

import java.util.stream.Stream;

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

    public NodeCollection<MissionAssignment> activeMissions() {
        return activeMissions;
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
