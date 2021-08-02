package old.core.box.configurator;

import old.core.box.checkers.MatchTimerChecker;
import old.core.box.checkers.MissionTimerChecker;
import old.core.box.events.GamificationEvent;
import old.core.model.Component;


public class GameLoopConfigurator {

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
}
