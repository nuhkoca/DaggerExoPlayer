package com.nuhkoca.myapplication.ui.video

import android.os.Bundle
import android.view.View

import com.google.android.exoplayer2.Player
import com.nuhkoca.myapplication.R
import com.nuhkoca.myapplication.databinding.ActivityVideoBinding
import com.nuhkoca.myapplication.helper.Constants
import com.nuhkoca.myapplication.util.exo.ExoUtil
import com.nuhkoca.myapplication.util.exo.ExoUtilFactory

import javax.inject.Inject

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Lazy
import dagger.android.support.DaggerAppCompatActivity

/**
 * A [DaggerAppCompatActivity] that handles media playing
 *
 * @author nuhkoca
 */
class VideoActivity : DaggerAppCompatActivity(), ExoUtil.PlayerStateListener {

    private lateinit var mActivityVideoBinding: ActivityVideoBinding
    private lateinit var exoUtil: ExoUtil

    @Inject internal lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject internal lateinit var exoUtilFactory: Lazy<ExoUtilFactory>

    /**
     * Initializes the activity
     *
     * @param savedInstanceState that indicates whether or not there is
     * an instances has been saved
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_video)
        val videoViewModel = ViewModelProviders.of(this, viewModelFactory).get(VideoViewModel::class.java)

        exoUtil = exoUtilFactory.get().exoUtil
        exoUtil.setPlayerView(mActivityVideoBinding.pvVideo)
        exoUtil.setListener(this)

        val extras = intent.extras ?: return
        val videoUrl = extras.getString(Constants.VIDEO_KEY)

        if (videoUrl != null) {
            videoViewModel.getPlayableContent(videoUrl)
            videoViewModel.content.observe(this, Observer {
                it?.let {
                    exoUtil.run {
                        setUrl(it.request!!.files!!.progressive!![2].url!!)
                        onStart()
                    }
                }
            })
        }
    }

    /**
     * Gets called when there is an error to play video
     */
    override fun onPlayerError() {
        mActivityVideoBinding.pbError.visibility = View.GONE
    }

    /**
     * Gets called when video is ready to be played.
     *
     * @param playbackState indicates the status of video
     */
    override fun onPlayerStateChanged(playbackState: Int) {
        when (playbackState) {
            Player.STATE_BUFFERING -> mActivityVideoBinding.pbError.visibility = View.VISIBLE
            Player.STATE_READY -> mActivityVideoBinding.pbError.visibility = View.GONE
            Player.STATE_IDLE -> mActivityVideoBinding.pbError.visibility = View.GONE
        }
    }

    /**
     * Clears references
     */
    public override fun onStart() {
        super.onStart()
        exoUtil.onStart()
    }

    /**
     * Regains references
     */
    public override fun onResume() {
        super.onResume()
        exoUtil.onResume()
    }

    /**
     * Clears references
     */
    public override fun onPause() {
        super.onPause()
        exoUtil.onPause()
    }

    /**
     * Regains references
     */
    public override fun onStop() {
        super.onStop()
        exoUtil.onStop()
    }
}
