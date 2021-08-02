package old.core.box.events.achievement;

import old.core.box.events.EventType;
import old.core.box.events.GamificationEvent;
import old.core.model.attributes.AchievementType;

public class CreateAchievement extends GamificationEvent {

    public CreateAchievement() {
        super(CreateAchievement.class);
    }

    public CreateAchievement(io.intino.alexandria.event.Event event) {
        super(event);
    }

    public CreateAchievement(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String world() {
        return get("context");
    }

    public String description() {
        return get("description");
    }

    public AchievementType type() {
        return getAsEnum("type", AchievementType.class);
    }

    public EventType eventInvolved() {
        return getAsEnum("eventInvolved", EventType.class);
    }

    public Integer maxCount() {
        return getAsInt("maxCount");
    }

    public CreateAchievement world(String context) {
        set("context", context);
        return this;
    }

    public CreateAchievement description(String description) {
        set("description", description);
        return this;
    }

    public CreateAchievement type(AchievementType type) {
        set("type", type);
        return this;
    }

    public CreateAchievement eventInvolved(EventType eventInvolved) {
        set("eventInvolved", eventInvolved);
        return this;
    }

    public CreateAchievement maxCount(int maxCount) {
        set("maxCount", maxCount);
        return this;
    }
}