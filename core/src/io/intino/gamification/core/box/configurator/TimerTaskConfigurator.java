package io.intino.gamification.core.box.configurator;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.checkers.MatchTimerChecker;
import io.intino.gamification.core.box.checkers.MissionTimerChecker;

import java.util.Timer;
import java.util.TimerTask;

import static io.intino.gamification.core.box.utils.TimeUtils.*;

public class TimerTaskConfigurator {

    private final CoreBox box;

    public TimerTaskConfigurator(CoreBox box) {
        this.box = box;
    }

    public void set(int amount, Scale scale) {
        run(getMillisOf(scale, amount));
    }

    private void run(long millis) {
        if(millis == 0) return;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                box.checker(MatchTimerChecker.class).check();
                box.checker(MissionTimerChecker.class).check();
            }
        }, 0, millis);
    }
}
