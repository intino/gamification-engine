package io.intino.gamification.core.box.configurator;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.checkers.MatchTimerChecker;
import io.intino.gamification.core.box.checkers.MissionTimerChecker;

import java.util.Timer;
import java.util.TimerTask;

import static io.intino.gamification.core.box.utils.TimeUtils.Scale;
import static io.intino.gamification.core.box.utils.TimeUtils.getMillisOf;

public class TimerTaskConfigurator {

    private final CoreBox box;
    private final Timer timer;
    private int amount;
    private Scale scale;

    public TimerTaskConfigurator(CoreBox box) {
        this.box = box;
        this.timer = new Timer();
    }

    public void schedule(int amount, Scale scale) {
        long millis = getMillisOf(scale, amount);
        if(millis == 0) return;
        this.amount = amount;
        this.scale = scale;
        timer.cancel();
        timer.scheduleAtFixedRate(new Task(), 0, millis);
    }

    private class Task extends TimerTask {
        @Override
        public void run() {
            box.checker(MatchTimerChecker.class).check(amount, scale);
            box.checker(MissionTimerChecker.class).check(amount, scale);
        }
    }
}
