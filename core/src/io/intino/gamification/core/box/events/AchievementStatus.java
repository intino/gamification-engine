package io.intino.gamification.core.box.events;

public class AchievementStatus extends GamificationEvent {

    public enum Status {
        Achieved, Failed
    }

    public AchievementStatus() {
        super("AchievementStatus");
    }

    public AchievementStatus(io.intino.alexandria.event.Event event) {
        super(event);
    }

    public AchievementStatus(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String match() {
        return get("match");
    }

    public String player() {
        return get("player");
    }

    public Status status() {
        return getAsEnum("status", Status.class);
    }

    public AchievementStatus match(String match) {
        set("match", match);
        return this;
    }

    public AchievementStatus points(String player) {
        set("player", player);
        return this;
    }

    public AchievementStatus status(Status status) {
        set("status", status);
        return this;
    }
}