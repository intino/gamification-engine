package io.intino.gamification.test;

import io.intino.gamification.Engine;

import static io.intino.gamification.core.box.utils.TimeUtils.Scale.Hour;

public class Test {

    public static void main(String[] args) {

        Engine engine = null;

        //engine.configuration().healthListener.set((entity, oldValue, newValue) -> oldValue);

        engine.configuration().gameLoopConfigurator.schedule(1, Hour);
    }
}


