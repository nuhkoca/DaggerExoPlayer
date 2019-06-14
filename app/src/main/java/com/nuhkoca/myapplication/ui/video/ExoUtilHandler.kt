package com.nuhkoca.myapplication.ui.video

import androidx.lifecycle.Lifecycle.Event.ON_PAUSE
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.nuhkoca.myapplication.util.exo.ExoUtil

/**
 * A lifecycle observer for [ExoUtil]
 *
 * @author nuhkoca
 */
class ExoUtilHandler(private val exoUtil: ExoUtil) : LifecycleObserver {

    /**
     * Regains references
     */
    @OnLifecycleEvent(ON_START)
    private fun onStart() {
        exoUtil.onStart()
    }

    /**
     * Regains references
     */
    @OnLifecycleEvent(ON_RESUME)
    private fun onResume() {
        exoUtil.onResume()
    }

    /**
     * Clears references
     */
    @OnLifecycleEvent(ON_PAUSE)
    private fun onPause() {
        exoUtil.onPause()
    }

    /**
     * Clears references
     */
    @OnLifecycleEvent(ON_STOP)
    private fun onStop() {
        exoUtil.onStop()
    }
}
