package old.core.box.events.mission;

import old.core.box.events.GamificationEvent;
import old.core.model.attributes.MissionState;

public class NewStateMission extends GamificationEvent {

    public NewStateMission() {
        super(NewStateMission.class);
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

    public MissionState state() {
        return getAsEnum("state", MissionState.class);
    }

    public String world() {
        return get("world");
    }

    public NewStateMission player(String player) {
        set("player", player);
        return this;
    }

    public NewStateMission state(MissionState state) {
        set("state", state);
        return this;
    }

    public NewStateMission world(String world) {
        set("world", world);
        return this;
    }
}