package io.intino.gamification.core.box.configurator;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.checkers.MatchTimerChecker;
import io.intino.gamification.core.box.checkers.MissionTimerChecker;

import java.util.Timer;
import java.util.TimerTask;

import static io.intino.gamification.core.box.utils.TimeUtils.*;

public class TimerTaskConfigurator {

    private final CoreBox box;
    private final Timer timer;

    public TimerTaskConfigurator(CoreBox box) {
        this.box = box;
        this.timer = new Timer();
    }

    public void schedule(int amount, Scale scale) {
        run(getMillisOf(scale, amount));
    }

    private synchronized void run(long millis) {
        if(millis == 0) return;
        timer.cancel();
        timer.scheduleAtFixedRate(new Task(), 0, millis);
    }

    private class Task extends TimerTask {
        @Override
        public void run() {
            box.checker(MatchTimerChecker.class).check();
            box.checker(MissionTimerChecker.class).check();
        }
    }
}
