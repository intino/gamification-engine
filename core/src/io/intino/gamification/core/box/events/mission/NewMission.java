package io.intino.gamification.core.box.events.mission;

import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.GamificationEvent;

import java.util.List;

public class NewMission extends GamificationEvent {

    public NewMission() {
        super(NewMission.class);
    }

    public NewMission(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public NewMission(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String world() {
        return get("world");
    }

    public List<String> players() {
        return getAsList("players");
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

    public EventType event() {
        return getAsEnum("event", EventType.class);
    }

    public Integer maxCount() {
        return getAsInt("maxCount");
    }

    public NewMission world(String world) {
        set("world", world);
        return this;
    }

    public NewMission players(List<String> players) {
        set("players", players);
        return this;
    }

    public NewMission difficulty(MissionDifficulty difficulty) {
        set("difficulty", difficulty);
        return this;
    }

    public NewMission type(MissionType type) {
        set("type", type);
        return this;
    }

    public NewMission description(String description) {
        set("description", description);
        return this;
    }

    public NewMission event(EventType event) {
        set("event", event);
        return this;
    }

    public NewMission maxCount(int maxCount) {
        set("maxCount", maxCount);
        return this;
    }
}