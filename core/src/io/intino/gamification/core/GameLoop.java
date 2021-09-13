package io.intino.gamification.core;

import io.intino.gamification.events.EventManager;
import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.util.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class GameLoop {

    private static final int FRAME_RATE = 60;

    private final GamificationCore core;
    private final EventManager eventManager;
    private final GamificationGraph graph;
    private final Timer timer;

    public GameLoop(GamificationCore core) {
        this.core = core;
        this.eventManager = core.eventManager();
        this.graph = core.graph();
        this.timer = new Timer();
    }

    public void start() {
        timer.schedule(new GameLoopUpdate(), 0, timeBetweenFrames());
    }

    public void stop() {
        timer.cancel();
    }

    private long timeBetweenFrames() {
        return (long) Math.max(1, 1000.0f / ((float) FRAME_RATE));
    }

    private class GameLoopUpdate extends TimerTask {

        private long lastFrameTime = System.currentTimeMillis();

        @Override
        public void run() {
            beginFrame();
            {
                core.graphSerializer().checkCronSave();
                eventManager.pollEvents();
                graph.update();
            }
            endFrame();
        }

        private void beginFrame() {
            final long now = System.currentTimeMillis();
            GameTime.deltaTime = now - lastFrameTime;
            lastFrameTime = now;
        }

        private void endFrame() {
            ++GameTime.frame;
            if(graph.shouldSave()) saveGraph();
        }

        private void saveGraph() {
            Logger.info("Saving graph...");
            final long start = System.currentTimeMillis();
            core.graphSerializer().save();
            final float time = (System.currentTimeMillis() - start);
            Logger.info("Graph serialized after: " + time + " ms");
        }
    }
}
