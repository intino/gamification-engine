package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.events.attributes.AchievementType;

public class ModifyAchievement extends GamificationEvent {

    public ModifyAchievement() {
        super("ModifyAchievement");
    }

    public ModifyAchievement(io.intino.alexandria.event.Event event) {
        super(event);
    }

    public ModifyAchievement(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String description() {
        return get("description");
    }

    public AchievementType type() {
        return getAsEnum("type", AchievementType.class);
    }

    public ModifyAchievement description(String description) {
        set("description", description);
        return this;
    }

    public ModifyAchievement type(AchievementType type) {
        set("type", type);
        return this;
    }
}