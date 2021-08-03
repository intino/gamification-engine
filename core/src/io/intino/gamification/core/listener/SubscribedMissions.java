package io.intino.gamification.core.listener;

import io.intino.gamification.events.GamificationEvent;

public interface SubscribedMissions {

    <T extends GamificationEvent> void notify(T event);
}
