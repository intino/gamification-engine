package io.intino.gamification.test;

import io.intino.gamification.GamificationEngine;
import io.intino.gamification.core.listener.SubscribedMissions;
import io.intino.gamification.events.CustomEvent;
import io.intino.gamification.events.GamificationEvent;
import io.intino.gamification.model.entity.Mission;

public class Main {

    public static void main(String[] args) {
        Mission mission = new Mission();

        mission.subscribe(CustomEvent.class, new SubscribedMissions() {
            @Override
            public <T extends GamificationEvent> void notify(T event) {

            }
        });

        GamificationEngine engine = null;
    }
}
