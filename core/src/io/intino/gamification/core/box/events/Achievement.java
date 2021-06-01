package io.intino.gamification.core.box.events;

import io.intino.gamification.core.box.events.enumerates.AchievementType;

public class Achievement extends GamificationEvent {

    public Achievement() {
        super("Achievement");
    }

    public Achievement(io.intino.alexandria.event.Event event) {
        super(event);
    }

    public Achievement(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public Double description() {
        return getAsDouble("description");
    }

    public AchievementType type() {
        return getAsEnum("type", AchievementType.class);
    }

    public Achievement description(Double description) {
        set("description", description);
        return this;
    }

    public Achievement type(AchievementType type) {
        set("type", type);
        return this;
    }
}