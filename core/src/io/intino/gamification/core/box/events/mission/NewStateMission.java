package io.intino.gamification.core.box.events.mission;

import io.intino.gamification.core.box.events.GamificationEvent;

public class NewStateMission extends GamificationEvent {

    public NewStateMission() {
        super("NewStateMission");
    }

    public NewStateMission(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public NewStateMission(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String player() {
        return get("player");
    }

    public String mission() {
        return get("mission");
    }

    public MissionState state() {
        return getAsEnum("state", MissionState.class);
    }

    public NewStateMission player(String player) {
        set("player", player);
        return this;
    }

    public NewStateMission mission(String mission) {
        set("mission", mission);
        return this;
    }

    public NewStateMission state(MissionState state) {
        set("state", state);
        return this;
    }
}