package io.intino.gamification.core;

import io.intino.gamification.core.checker.TimeChecker;
import io.intino.gamification.core.exception.InvalidAttributeValueException;
import io.intino.gamification.utils.time.Scale;

import java.util.Timer;
import java.util.TimerTask;

public class GameLoop {

    private final int FRAME_RATE = 60;

    private final Core core;

    private Timer timer;

    public GameLoop(Core core) {
        this.core = core;
        this.schedule();
    }

    private void schedule() {
        if(FRAME_RATE <= 0) {
            //TODO REGISTRAR ERROR
            throw new InvalidAttributeValueException("frameRate", String.valueOf(FRAME_RATE), "The value must be 1 or more.");
        }

        scheduleGameLoop(millisToRefresh(FRAME_RATE));
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

    private long millisToRefresh(int frameRate) {
        return (long) Math.max(1, 1000 / ((float) FRAME_RATE));
    }

    private class GameLoopUpdate extends TimerTask {
        @Override
        public void run() {
            core.checker(TimeChecker.class).check((int) millisToRefresh(FRAME_RATE), Scale.Millis);
        }
    }
}
