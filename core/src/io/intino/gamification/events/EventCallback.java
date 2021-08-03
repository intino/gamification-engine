package io.intino.gamification.events;

@FunctionalInterface
public interface EventCallback<T extends GamificationEvent> {

    void notify(T event);
}
