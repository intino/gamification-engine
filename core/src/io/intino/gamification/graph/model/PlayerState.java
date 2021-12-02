package io.intino.gamification.graph.model;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.graph.structure.Fact;

import java.util.Objects;
import java.util.stream.Stream;

import static io.intino.gamification.util.data.Progress.State.Complete;
import static io.intino.gamification.util.data.Progress.State.InProgress;

public final class PlayerState extends Node {

    private final NodeCollection<MissionAssignment> missionAssignments = new NodeCollection<>();

    PlayerState(String id) {
        super(id);
    }

    @Override
    void init() {
        missionAssignments.init(absoluteId());
    }

    @Override
    void destroyChildren() {
        missionAssignments.forEach(Node::markAsDestroyed);
    }

    // Facts from finished matches
    public Stream<Fact> facts() {
        if(parent() == null) return Stream.empty();
        return parent().rounds().stream()
                .filter(round -> round.state() == Round.State.Finished)
                .flatMap(this::factsOfMatch);
    }

    // All facts, including those in the current round
    public Stream<Fact> rawFacts() {
        if(parent() == null) return Stream.empty();
        return parent().rounds().stream()
                .flatMap(this::factsOfMatch);
    }

    private Stream<Fact> factsOfMatch(Round round) {
        Round.Match match = round.matches().addIfNotExists(id(), () -> new Round.Match(id()));
        return match.facts().stream();
    }

    // Score from finished matches
    public int score() {
        if(parent() == null) return 0;

        int score = 0;

        for(Round round : parent().rounds()) {
            if(round.state() != Round.State.Finished) continue;
            Round.Match match = round.matches().find(id());
            if(match != null) score += Math.max(match.score(), 0);
        }

        return Math.max(score, 0);
    }

    // Score, including points gained during the current round
    public int rawScore() {
        if(parent() == null) return 0;

        int score = 0;

        for(Round round : parent().rounds()) {
            Round.Match match = round.matches().find(id());
            if(match != null) score += Math.max(match.score(), 0);
        }

        return Math.max(score, 0);
    }

    void assignMission(MissionAssignment missionAssignment) {
        missionAssignments.add(missionAssignment);
    }

    void endMissions() {
        missionAssignments.forEach(ma -> {
            if(parent().endWithinThisSeason(ma) && ma.progress().state() == InProgress) {
                ma.update(ma.progress().state());
                ma.progress().fail();
            }
        });
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

    public final NodeCollection<MissionAssignment> missionAssignments() {
        return missionAssignments;
    }

    private Competition competition() {
        return parent().parent();
    }

    public final Season season() {
        return parent();
    }

    @Override
    public final Season parent() {
        String[] ids = parentIds();
        if(ids == null || ids.length == 0) return null;
        return GamificationGraph.get()
                .competitions().find(ids[0])
                .seasons().find(ids[1]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PlayerState that = (PlayerState) o;
        return Objects.equals(missionAssignments, that.missionAssignments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), missionAssignments);
    }

    @Override
    public String toString() {
        return "PlayerState{" +
                "missionAssignments=" + missionAssignments +
                '}';
    }
}
