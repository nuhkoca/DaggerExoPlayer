package com.nuhkoca.myapplication.helper

import android.os.Handler
import android.os.Looper

import java.util.concurrent.Executor
import java.util.concurrent.Executors

import javax.inject.Inject

/**
 * A class that makes I/O, background or UI based processes.
 *
 * @param diskIO    represents background thread
 * @param networkIO represents network threads
 *
 * @author nuhkoca
 */
class AppExecutors @Inject constructor() {
    private val mainIO: Executor
    private val diskIO: Executor
    private val networkIO: Executor

    init {
        this.mainIO = MainThreadExecutor()
        this.diskIO = Executors.newSingleThreadExecutor()
        this.networkIO = Executors.newFixedThreadPool(Constants.EXECUTOR_THREAD_POOL_OFFSET)
    }

    /**
     * A class for [mainIO]
     */
    internal class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    /**
     * Returns network executor
     *
     * @return [Executor]
     */
    fun networkIO(): Executor {
        return networkIO
    }

    /**
     * Returns I/O executor
     *
     * @return [Executor]
     */
    fun diskIO(): Executor {
        return diskIO
    }

    /**
     * Return UI executor
     *
     * @return [Executor]
     */
    fun mainIO(): Executor {
        return mainIO
    }
}
