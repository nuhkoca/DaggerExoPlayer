package com.nuhkoca.myapplication.helper;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import androidx.annotation.NonNull;

/**
 * A class that makes I/O, background or UI based processes.
 *
 * @author nuhkoca
 */
public class AppExecutors {
    private final Executor networkIO;
    private final Executor diskIO;
    private final Executor mainIO;

    @Inject
    AppExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(Constants.EXECUTOR_THREAD_POOL_OFFSET));
    }

    /**
     * Default constructor
     *
     * @param diskIO    represents background threads
     * @param networkIO represents network threads
     */
    private AppExecutors(Executor diskIO, Executor networkIO) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainIO = new MainThreadExecutor();
    }

    /**
     * A class for {@link #mainIO}
     */
    static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

    /**
     * Returns network executor
     *
     * @return {@link Executor}
     */
    public Executor networkIO() {
        return networkIO;
    }

    /**
     * Returns I/O executor
     *
     * @return {@link Executor}
     */
    public Executor diskIO() {
        return diskIO;
    }

    /**
     * Return UI executor
     *
     * @return {@link Executor}
     */
    public Executor mainIO() {
        return mainIO;
    }
}
