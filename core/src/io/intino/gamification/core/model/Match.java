package io.intino.gamification.core.model;

import io.intino.gamification.core.model.attributes.MatchState;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class Match extends Component {

    private final String worldId;
    private final Instant from;
    private final Instant to;
    private final MatchState state;
    private final boolean reboot;
    private final List<PlayerState> playersState;
    private final List<Mission> missions;
    private final List<Achievement> achievements;

    public Match(io.intino.gamification.core.graph.Match match) {
        super(match.id());
        this.worldId = match.worldId();
        this.from = match.from();
        this.to = match.to();
        this.state = match.state();
        this.reboot = match.reboot();
        this.playersState = match.playersState().stream().map(PlayerState::new).collect(Collectors.toList());
        this.missions = match.missions().stream().map(Mission::new).collect(Collectors.toList());
        this.achievements = match.achievements().stream().map(Achievement::new).collect(Collectors.toList());
    }

    public String worldId() {
        return worldId;
    }

    public Instant from() {
        return from;
    }

    public Instant to() {
        return to;
    }

    public MatchState state() {
        return state;
    }

    public boolean reboot() {
        return reboot;
    }

    public List<PlayerState> playersState() {
        return playersState;
    }

    public List<Mission> missions() {
        return missions;
    }

    public List<Achievement> achievements() {
        return achievements;
    }

    public static class PlayerState {

        private final String playerId;
        private final int score;
        private final List<MissionState> missionsState;
        private final List<AchievementState> achievementsState;

        public PlayerState(io.intino.gamification.core.graph.PlayerState playerState) {
            this.playerId = playerState.playerId();
            this.score = playerState.score();
            this.missionsState = playerState.missionState().stream().map(MissionState::new).collect(Collectors.toList());
            this.achievementsState = playerState.achievements().stream().map(AchievementState::new).collect(Collectors.toList());
        }

        public String playerId() {
            return playerId;
        }

        public int score() {
            return score;
        }

        public List<MissionState> missionsState() {
            return missionsState;
        }

        public List<AchievementState> achievementsState() {
            return achievementsState;
        }

        public static class MissionState {

            private final String missionId;
            private final io.intino.gamification.core.model.attributes.MissionState state;
            private final int count;

            public MissionState(io.intino.gamification.core.graph.MissionState missionState) {
                this.missionId = missionState.missionId();
                this.state = missionState.state();
                this.count = missionState.count();
            }

            public String missionId() {
                return missionId;
            }

            public io.intino.gamification.core.model.attributes.MissionState state() {
                return state;
            }

            public int count() {
                return count;
            }
        }

        public static class AchievementState {

            private final String achievementId;
            private final io.intino.gamification.core.model.attributes.AchievementState state;
            private final int count;

            public AchievementState(io.intino.gamification.core.graph.AchievementState achievementState) {
                this.achievementId = achievementState.achievementId();
                this.state = achievementState.state();
                this.count = achievementState.count();
            }

            public String missionId() {
                return achievementId;
            }

            public io.intino.gamification.core.model.attributes.AchievementState state() {
                return state;
            }

            public int count() {
                return count;
            }
        }
    }
}