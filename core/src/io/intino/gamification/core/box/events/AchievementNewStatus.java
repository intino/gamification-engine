package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.events.enumerates.AchievementState;

public class AchievementNewStatus extends GamificationEvent {

    public AchievementNewStatus() {
        super("AchievementStatus");
    }

    public AchievementNewStatus(io.intino.alexandria.event.Event event) {
        super(event);
    }

    public AchievementNewStatus(io.intino.alexandria.message.Message message) {
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

    public AchievementNewStatus match(String match) {
        set("match", match);
        return this;
    }

    public AchievementNewStatus points(String player) {
        set("player", player);
        return this;
    }

    public AchievementNewStatus status(AchievementState status) {
        set("status", status);
        return this;
    }
}