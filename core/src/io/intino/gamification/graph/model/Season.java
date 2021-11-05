package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.Property;
import io.intino.gamification.util.time.TimeUtils;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.intino.gamification.util.data.Progress.State.InProgress;

public class Season extends CompetitionNode {

    private NodeCollection<Round> rounds;
    private NodeCollection<PlayerState> playerStates;
    //private NodeCollection<ObtainedAchievement> obtainedAchievements;
    private final Property<Instant> startTime = new Property<>();
    private final Property<Instant> endTime = new Property<>();
    private final Property<State> state = new Property<>(State.Created);

    public Season(String id) {
        super(id);
    }

    @Override
    void init() {
        this.rounds = new NodeCollection<>(competitionId());
        this.playerStates = new NodeCollection<>(competitionId());
        //this.obtainedAchievements = new NodeCollection<>(competitionId());
    }

    public final void injectPersistencePlayerState(List<PlayerState> persistencePlayerState) {
        this.playerStates().addAll(clean(persistencePlayerState));
    }

    State state() {
        return state.get();
    }

    void begin() {
        startTime.set(TimeUtils.currentInstant());
        onBegin();
        state.set(State.Running);
    }

    void end() {
        endTime.set(TimeUtils.currentInstant());
        endMissions();
        onEnd();
        state.set(State.Finished);
    }

    public final void startNewRound(Round round) {

        if (round != null) {
            rounds.add(round);
            round.begin();
        }
    }

    public final void finishCurrentRound() {
        Round currentRound = rounds.last();
        if(currentRound != null && currentRound.isAvailable()) currentRound.end();
    }

    public Round currentRound() {
        if(rounds.isEmpty()) return null;
        Round round = rounds.last();
        return round.state() != Round.State.Finished ? null : round;
    }

    public final List<PlayerState> persistencePlayerState() {
        return playerStates.stream()
                .map(this::filter)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void endMissions() {

        Season season = competition().currentSeason();
        if(season == null) return;

        missionAssignmentsOf(season.playerStates()).forEach(ma -> {
            if(endWithinThisSeason(ma) && ma.progress().state() == InProgress) {
                ma.update(ma.progress().state());
                ma.progress().fail();
            }
        });
    }

    private List<MissionAssignment> missionAssignmentsOf(NodeCollection<PlayerState> players) {
        return players.stream()
                .filter(ps -> ps.player() != null)
                .filter(ps -> ps.player().isAvailable())
                .map(PlayerState::missionAssignments)
                .flatMap(NodeCollection::stream)
                .collect(Collectors.toList());
    }

    private PlayerState filter(PlayerState playerState) {
        PlayerState newPlayerState = playerState.copy();
        newPlayerState.missionAssignments().removeIf(this::endWithinThisSeason);
        if(newPlayerState.missionAssignments().size() == 0) return null;
        return newPlayerState;
    }

    private boolean endWithinThisSeason(MissionAssignment missionAssignment) {
        return missionAssignment.hasExpired() || missionAssignment.expirationTime().endsWithMatch();
    }

    private List<PlayerState> clean(List<PlayerState> persistencePlayerState) {
        persistencePlayerState.forEach(pps -> pps.facts().clear());
        return persistencePlayerState;
    }

    public final NodeCollection<Round> rounds() {
        //TODO Devolver unmodifiable
        return rounds;
    }

    public final NodeCollection<PlayerState> playerStates() {
        return playerStates;
    }

    protected void onBegin() {}
    protected void onEnd() {}

    public enum State {
        Created, Running, Finished
    }
}