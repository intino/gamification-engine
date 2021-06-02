package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.events.attributes.AchievementState;

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

    public String match() {
        return get("match");
    }

    public String player() {
        return get("player");
    }

    public AchievementState status() {
        return getAsEnum("status", AchievementState.class);
    }

    public AchievementNewState match(String match) {
        set("match", match);
        return this;
    }

    public AchievementNewState player(String player) {
        set("player", player);
        return this;
    }

    public AchievementNewState status(AchievementState status) {
        set("status", status);
        return this;
    }
}