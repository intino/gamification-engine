package io.intino.gamification.core.box.events;

public class DeleteAchievement extends GamificationEvent {

    public DeleteAchievement() {
        super("DeleteAchievement");
    }

    public DeleteAchievement(io.intino.alexandria.event.Event event) {
        super(event);
    }

    public DeleteAchievement(io.intino.alexandria.message.Message message) {
        super(message);
    }
}