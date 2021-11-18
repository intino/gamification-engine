package io.intino.gamification.events;

@FunctionalInterface
public interface EventCallback<T> {

    void notify(T progress);
}
