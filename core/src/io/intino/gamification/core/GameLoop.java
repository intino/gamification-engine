package io.intino.gamification.core;

import io.intino.gamification.core.exception.InvalidAttributeValueException;

import java.util.*;
import java.util.function.Consumer;

public class GameLoop {

    private final Core core;

    private Timer timer;

    //TODO COLA Y ACTUAR SOBRE LOS EVENTOS
    //private final Queue<GamificationEventWrapper<? extends GamificationEvent, ? extends Entity>> eventQueue;

    public GameLoop(Core core) {
        this.core = core;
        //this.eventQueue = new PriorityQueue<>(Comparator.comparing(w -> w.event().ts()));
        this.schedule(this.core.configuration().frameRate.get());
    }

    private void schedule(int frameRate) {
        if(frameRate <= 0) {
            //TODO REGISTRAR ERROR
            throw new InvalidAttributeValueException("frameRate", String.valueOf(frameRate), "The value must be 1 or more.");
        }

        scheduleGameLoop((int) (1000 / ((float) frameRate)));
    }

    private void scheduleGameLoop(long millis) {
        if(timer != null) stopPreviousLoop();
        timer = new Timer();
        timer.scheduleAtFixedRate(new GameLoopUpdate(), 0, millis);
    }

    private void stopPreviousLoop() {
        timer.cancel();
        timer.purge();
    }

    private class GameLoopUpdate extends TimerTask {
        @Override
        public void run() {
            //runCheckers();
            //feedEvents();
        }
    }

    /*public static class GamificationEventWrapper<T extends GamificationEvent, S extends Entity> {

        private final T event;
        private final EventProcessListener<S> listener;

        public GamificationEventWrapper(T event) {
            this.event = event;
            this.listener = null;
        }

        public GamificationEventWrapper(T event, EventProcessListener<S> listener) {
            this.event = event;
            this.listener = listener;
        }

        public T event() {
            return event;
        }

        public EventProcessListener<S> listener() {
            return listener;
        }
    }*/
}
