package io.intino.gamification.core.box.configurator;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.checkers.MatchTimerChecker;
import io.intino.gamification.core.box.checkers.MissionTimerChecker;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.listeners.EventProcessListener;
import io.intino.gamification.core.exception.InvalidAttributeValueException;
import io.intino.gamification.core.model.Component;

import java.util.*;

import static io.intino.gamification.core.box.utils.TimeUtils.Scale;
import static io.intino.gamification.core.box.utils.TimeUtils.getMillisOf;

public class GameLoopConfigurator {

    private final CoreBox box;

    private final Queue<GamificationEventWrapper<? extends GamificationEvent, ? extends Component>> eventQueue;

    private Timer timer;
    private int amount;
    private Scale scale;

    public GameLoopConfigurator(CoreBox box) {
        this.box = box;
        this.eventQueue = new PriorityQueue<>(Comparator.comparing(w -> w.event().ts()));
    }

    public <T extends GamificationEvent, S extends Component> void enqueue(T event) {

        synchronized (eventQueue) {
            GamificationEventWrapper<T, S> wrapper = new GamificationEventWrapper<>(event);
            eventQueue.add(wrapper);
        }
    }

    public <T extends GamificationEvent, S extends Component> void enqueue(T event, EventProcessListener<S> listener) {

        synchronized (eventQueue) {
            GamificationEventWrapper<T, S> wrapper = new GamificationEventWrapper<>(event, listener);
            eventQueue.add(wrapper);
        }
    }

    public void schedule(int amount, Scale scale) {
        if(amount <= 0) {
            throw new InvalidAttributeValueException("amount", String.valueOf(amount), "The value must be 1 or more.");
        }
        if(scale == null) {
            throw new InvalidAttributeValueException("scale", "null", "The value can't be null.");
        }
        this.amount = amount;
        this.scale = scale;
        scheduleGameLoop(getMillisOf(scale, amount));
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

    private void runCheckers() {
        box.checker(MissionTimerChecker.class).check(amount, scale);
        box.checker(MatchTimerChecker.class).check(amount, scale);
    }

    private void feedEvents() {

        synchronized (eventQueue) {
            while (!eventQueue.isEmpty()) {
                GamificationEventWrapper<? extends GamificationEvent, ? extends Component> wrapper = eventQueue.poll();
                if(wrapper != null) {
                    Component component = box.terminal().feed(wrapper.event());
                    if(wrapper.listener() != null && component != null) {
                        wrapper.listener().process(component);
                    }
                }
            }
        }
    }

    private class GameLoopUpdate extends TimerTask {
        @Override
        public void run() {
            runCheckers();
            feedEvents();
        }
    }

    public static class GamificationEventWrapper<T extends GamificationEvent, S extends Component> {

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
    }
}
