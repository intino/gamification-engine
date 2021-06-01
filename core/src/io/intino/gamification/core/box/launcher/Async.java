package io.intino.gamification.core.box.launcher;

import io.intino.alexandria.logger.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Async {

    protected final ExecutorService thread;
    protected final AtomicBoolean running;
    protected Runnable onStart;

    public Async() {
        this.thread = Executors.newSingleThreadExecutor(this::createThread);
        this.running = new AtomicBoolean();
        this.onStart = () -> {};
    }

    protected Thread createThread(Runnable runnable) {
        return new Thread(runnable, getClass().getSimpleName() + "-Thread");
    }

    public void onStart(Runnable onStartCallback) {
        this.onStart = onStartCallback != null ? onStartCallback : () -> {};
    }

    public boolean start() {
        if(!running.compareAndSet(false, true)) {
            Logger.warn("Model " + hashCode() + " is already running");
            return false;
        }
        thread.submit(this::run);
        return true;
    }

    public boolean running() {
        return running.get();
    }

    public boolean stop() {
        return stop(0, TimeUnit.SECONDS);
    }

    public boolean stop(long timeout, TimeUnit timeUnit) {
        if(!running.compareAndSet(true, false)) return false;
        try {
            thread.shutdown();
            thread.awaitTermination(timeout, timeUnit);
        } catch (InterruptedException e) {
            Logger.error(e);
        }
        return true;
    }

    protected abstract void run();
}
