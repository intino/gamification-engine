package io.tetrabot.graph.model;

import io.tetrabot.util.time.TimeUtils;
import io.tetrabot.util.data.Progress;

import java.time.Instant;
import java.util.Comparator;
import java.util.stream.Stream;

public final class PlayerState extends Node {

    private final NodeCollection<MissionAssignment> activeMissions;
    private final NodeCollection<MissionAssignment> finishedMissions;

    PlayerState(String id) {
        super(id);
        activeMissions = new NodeCollection<>();
        activeMissions.init(this, MissionAssignment.class);

        finishedMissions = new NodeCollection<>();
        finishedMissions.init(this, MissionAssignment.class);
    }

    @Override
    void onInit() {
    }

    public void assignMission(MissionAssignment assignment) {
        if(assignment.state() == Progress.State.InProgress) {
            activeMissions.add(assignment);
            activeMissions.sort(Comparator.comparing(MissionAssignment::startTime));
        } else {
            finishedMissions.add(assignment);
            finishedMissions.sort(Comparator.comparing(MissionAssignment::endTime));
        }
    }

    public void updateMission(MissionAssignment assignment) {
        if(assignment.state() != Progress.State.InProgress) return;
        assignment.update();
        if(assignment.state() != Progress.State.InProgress) {
            activeMissions.remove(assignment);
            finishedMissions.add(assignment);
            finishedMissions.sort(Comparator.comparing(MissionAssignment::endTime));
        }
    }

    public void completeMission(MissionAssignment assignment) {
        if(assignment.state() != Progress.State.InProgress) return;
        assignment.complete();
        activeMissions.remove(assignment);
        finishedMissions.add(assignment);
        finishedMissions.sort(Comparator.comparing(MissionAssignment::endTime));
    }

    public void failMission(MissionAssignment assignment) {
        if(assignment.state() != Progress.State.InProgress) return;
        assignment.fail();
        activeMissions.remove(assignment);
        finishedMissions.add(assignment);
        finishedMissions.sort(Comparator.comparing(MissionAssignment::endTime));
    }

    public void removeActiveMission(MissionAssignment assignment) {
        activeMissions.remove(assignment);
    }

    public void removeFinishedMission(MissionAssignment assignment) {
        finishedMissions.remove(assignment);
    }

    public void removeAllActiveMissions() {
        activeMissions.removeAll();
    }

    public void removeAllFinishedMissions() {
        finishedMissions.removeAll();
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

    public int scoreBetween(Instant start, Instant end) {
        if(parent() == null) return 0;

        int score = 0;

        boolean endReached = false;

        NodeCollection<Round> rounds = season().rounds();

        for(int i = 0; i < rounds.size() && !endReached; i++) {
            Round round = rounds.get(i);
            Match match = round.matches().find(id());
            if(match == null) continue;

            for(Fact fact : match.facts()) {
                if(TimeUtils.inRange(fact.ts(), start, end)) {
                    score += fact.points();
                } else {
                    endReached = true;
                    break;
                }
            }
        }

        return Math.max(score, 0);
    }

    public UnmodifiableNodeCollection<MissionAssignment> activeMissions() {
        return activeMissions;
    }

    public UnmodifiableNodeCollection<MissionAssignment> finishedMissions() {
        return finishedMissions;
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
                "id=" + id() +
                ", activeMissions=" + activeMissions.size() +
                ", finishedMissions=" + finishedMissions.size() +
                ", facts=" + rawFacts().count() +
                ", score=" + rawScore() +
                '}';
    }
}
