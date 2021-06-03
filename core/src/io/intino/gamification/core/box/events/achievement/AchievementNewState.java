package io.intino.gamification.core.box.events.achievement;

import io.intino.gamification.core.box.events.GamificationEvent;

public class AchievementNewState extends GamificationEvent {

    public AchievementNewState() {
        super("AchievementNewState");
    }

    public AchievementNewState(io.intino.alexandria.event.Event event) {
        super(event);
    }

    public AchievementNewState(io.intino.alexandria.message.Message message) {
        super(message);
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
}