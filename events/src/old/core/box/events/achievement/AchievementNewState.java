package old.core.box.events.achievement;

import old.core.box.events.GamificationEvent;
import old.core.model.attributes.AchievementState;
import old.core.model.attributes.AchievementType;

public class AchievementNewState extends GamificationEvent {

    public AchievementNewState() {
        super(AchievementNewState.class);
    }

    public AchievementNewState(io.intino.alexandria.event.Event event) {
        super(event);
    }

    public AchievementNewState(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String world() {
        return get("context");
    }

    public AchievementNewState world(String context) {
        set("context", context);
        return this;
    }

    public String player() {
        return get("player");
    }

    public AchievementState state() {
        return getAsEnum("state", AchievementState.class);
    }

    public AchievementNewState player(String player) {
        set("player", player);
        return this;
    }

    public AchievementNewState state(AchievementState state) {
        set("state", state);
        return this;
    }

    public AchievementType type() {
        return getAsEnum("type", AchievementType.class);
    }

    public AchievementNewState type(AchievementType type) {
        set("type", type);
        return this;
    }
}