package io.intino.gamification.core;

import io.intino.gamification.core.exception.InvalidAttributeValueException;

import java.util.Timer;
import java.util.TimerTask;

public class GameLoop {

    private final Core core;

    private Timer timer;

    public GameLoop(Core core) {
        this.core = core;
        this.schedule(60);
    }

    private void schedule(int frameRate) {
        if(frameRate <= 0) {
            //TODO REGISTRAR ERROR
            throw new InvalidAttributeValueException("frameRate", String.valueOf(frameRate), "The value must be 1 or more.");
        }

        scheduleGameLoop((long) Math.max(1, 1000 / ((float) frameRate)));
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

    private static class GameLoopUpdate extends TimerTask {
        @Override
        public void run() {
            //runCheckers();
        }
    }
}
