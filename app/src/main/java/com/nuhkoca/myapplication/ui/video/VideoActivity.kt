package com.nuhkoca.myapplication.ui.video

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.Player.STATE_BUFFERING
import com.google.android.exoplayer2.Player.STATE_IDLE
import com.google.android.exoplayer2.Player.STATE_READY
import com.nuhkoca.myapplication.R
import com.nuhkoca.myapplication.databinding.ActivityVideoBinding
import com.nuhkoca.myapplication.helper.Constants
import com.nuhkoca.myapplication.util.exo.ExoUtil
import com.nuhkoca.myapplication.util.exo.ExoUtilFactory
import com.nuhkoca.myapplication.util.ext.get
import com.nuhkoca.myapplication.util.ext.hide
import com.nuhkoca.myapplication.util.ext.isVisible
import com.nuhkoca.myapplication.util.ext.lifecycleAwareHandler
import com.nuhkoca.myapplication.util.ext.observeWith
import com.nuhkoca.myapplication.util.ext.unsafeLazy
import dagger.Lazy
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * A [DaggerAppCompatActivity] that handles media playing
 *
 * @author nuhkoca
 */
class VideoActivity : DaggerAppCompatActivity(), ExoUtil.PlayerStateListener {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var videoViewModel: VideoViewModel

    @Inject
    internal lateinit var exoUtilFactory: Lazy<ExoUtilFactory>

    private lateinit var mActivityVideoBinding: ActivityVideoBinding
    private lateinit var exoUtil: ExoUtil

    private val extras by unsafeLazy { intent.extras }

    /**
     * Initializes the activity
     *
     * @param savedInstanceState that indicates whether or not there is
     * an instances has been saved
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_video)
        videoViewModel = viewModelFactory.get(this)

        exoUtil = exoUtilFactory.get().exoUtil
        exoUtil.setPlayerView(mActivityVideoBinding.pvVideo)
        exoUtil.setListener(this)

        val videoUrl = extras?.getString(Constants.VIDEO_KEY)
        videoUrl?.let {
            videoViewModel.getPlayableContent(it)
            videoViewModel.content.observeWith(this) { response ->
                exoUtil.setUrl(response.request?.files?.progressive?.get(INDEX_URL)?.url)
            }
        }

        lifecycleAwareHandler(this, exoUtil)
    }

    /**
     * Gets called when there is an error to play video
     */
    override fun onPlayerError() {
        mActivityVideoBinding.pbError.hide()
    }

    /**
     * Gets called when video is ready to be played.
     *
     * @param playbackState indicates the status of video
     */
    override fun onPlayerStateChanged(playbackState: Int) {
        mActivityVideoBinding.pbError.isVisible =
            playbackState == STATE_BUFFERING || playbackState != STATE_READY || playbackState != STATE_IDLE
    }

    companion object {
        private const val INDEX_URL = 2
    }
}
