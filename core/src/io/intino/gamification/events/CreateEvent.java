package io.intino.gamification.events;

public interface CreateEvent {
    String customParams();
    GamificationEvent customParams(String customParams);
}
