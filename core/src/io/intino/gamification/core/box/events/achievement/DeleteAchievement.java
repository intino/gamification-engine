package io.intino.gamification.core.box.events.achievement;

import io.intino.gamification.core.box.events.GamificationEvent;

public class DeleteAchievement extends GamificationEvent {

    public DeleteAchievement() {
        super(DeleteAchievement.class);
    }

    public DeleteAchievement(io.intino.alexandria.event.Event event) {
        super(event);
    }

    public DeleteAchievement(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String world() {
        return get("context");
    }

    public AchievementType type() {
        return getAsEnum("type", AchievementType.class);
    }

    public DeleteAchievement world(String context) {
        set("context", context);
        return this;
    }

    public DeleteAchievement type(AchievementType type) {
        set("type", type);
        return this;
    }
}