package io.intino.gamification.core.box.events.mission;

import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.GamificationEvent;

import java.time.Instant;
import java.util.List;

public class CreateMission extends GamificationEvent {

    public CreateMission() {
        super(CreateMission.class);
    }

    public CreateMission(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public CreateMission(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String world() {
        return get("world");
    }

    public List<String> players() {
        return getAsList("players");
    }

    public Instant expiration() {
        return getAsInstant("expiration");
    }

    public MissionDifficulty difficulty() {
        return getAsEnum("difficulty", MissionDifficulty.class);
    }

    public MissionType type() {
        return getAsEnum("type", MissionType.class);
    }

    public String description() {
        return get("description");
    }

    public EventType eventInvolved() {
        return getAsEnum("eventInvolved", EventType.class);
    }

    public Integer maxCount() {
        return getAsInt("maxCount");
    }

    public CreateMission world(String world) {
        set("world", world);
        return this;
    }

    public CreateMission players(List<String> players) {
        set("players", players);
        return this;
    }

    public CreateMission expiration(Instant expiration) {
        set("expiration", expiration);
        return this;
    }

    public CreateMission difficulty(MissionDifficulty difficulty) {
        set("difficulty", difficulty);
        return this;
    }

    public CreateMission type(MissionType type) {
        set("type", type);
        return this;
    }

    public CreateMission description(String description) {
        set("description", description);
        return this;
    }

    public CreateMission eventInvolved(EventType eventInvolved) {
        set("eventInvolved", eventInvolved);
        return this;
    }

    public CreateMission maxCount(int maxCount) {
        set("maxCount", maxCount);
        return this;
    }
}