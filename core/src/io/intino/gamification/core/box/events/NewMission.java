package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.events.attributes.MissionDifficulty;
import io.intino.gamification.core.box.events.attributes.MissionType;

public class NewMission extends GamificationEvent {

    public NewMission() {
        super("NewMission");
    }

    public NewMission(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public NewMission(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String player() {
        return get("player");
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

    public NewMission player(String player) {
        set("player", player);
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
}