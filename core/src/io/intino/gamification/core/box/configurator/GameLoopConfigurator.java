package io.intino.gamification.core.box.configurator;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.checkers.MatchTimerChecker;
import io.intino.gamification.core.box.checkers.MissionTimerChecker;
import io.intino.gamification.core.exception.InvalidAttributeValueException;

import java.util.Timer;
import java.util.TimerTask;

import static io.intino.gamification.core.box.utils.TimeUtils.Scale;
import static io.intino.gamification.core.box.utils.TimeUtils.getMillisOf;

public class GameLoopConfigurator {

    private final CoreBox box;
    private Timer timer;
    private int amount;
    private Scale scale;

    public GameLoopConfigurator(CoreBox box) {
        this.box = box;
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

    private class GameLoopUpdate extends TimerTask {
        @Override
        public void run() {
            box.checker(MatchTimerChecker.class).check(amount, scale);
            box.checker(MissionTimerChecker.class).check(amount, scale);
        }
    }
}
