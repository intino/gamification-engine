package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.events.attributes.AchievementType;

public class CreateAchievement extends GamificationEvent {

    public CreateAchievement() {
        super("CreateAchievement");
    }

    public CreateAchievement(io.intino.alexandria.event.Event event) {
        super(event);
    }

    public CreateAchievement(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String description() {
        return get("description");
    }

    public AchievementType type() {
        return getAsEnum("type", AchievementType.class);
    }

    public CreateAchievement description(String description) {
        set("description", description);
        return this;
    }

    public CreateAchievement type(AchievementType type) {
        set("type", type);
        return this;
    }
}