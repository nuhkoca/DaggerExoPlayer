package com.nuhkoca.myapplication.util.exo

import android.net.Uri

import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.PlaybackPreparer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util
import com.nuhkoca.myapplication.helper.Constants
import com.nuhkoca.myapplication.util.PreferenceUtil

import javax.inject.Inject
import javax.inject.Provider

/**
 * A utility class to conduct Exo implementation
 *
 * @author nuhkoca
 */
class ExoUtil
/**
 * A default constructor that injects dependencies
 *
 * @param exoPlayer      represents an instance of [SimpleExoPlayer]
 * @param factory        represents an instance of [ExtractorMediaSource.Factory]
 * @param preferenceUtil represents an instance of [PreferenceUtil]
 */
@Inject
internal constructor(
    private val exoPlayer: Provider<SimpleExoPlayer>,
    private val factory: ProgressiveMediaSource.Factory,
    private val preferenceUtil: PreferenceUtil
) : PlaybackPreparer, Player.EventListener {

    private var simpleExoPlayer: SimpleExoPlayer? = null

    private var mPlayerStateListener: PlayerStateListener? = null
    private var mPlayerView: PlayerView? = null

    private var mShouldAutoPlay: Boolean = false
    private var mUrl: String? = null

    init {

        mShouldAutoPlay = true
    }

    /**
     * Helps build a [ExtractorMediaSource.Factory]
     *
     * @param uri represents a url to be played
     * @return an instance of [MediaSource]
     */
    private fun buildMediaSource(uri: Uri?): MediaSource {
        return factory.createMediaSource(uri)
    }

    fun setListener(playerStateListener: PlayerStateListener) {
        this.mPlayerStateListener = playerStateListener
    }

    /**
     * Helps set a [PlayerView] in order to play media
     *
     * @param playerView represents a [PlayerView]
     */
    fun setPlayerView(playerView: PlayerView) {
        this.mPlayerView = playerView
    }

    /**
     * Helps set a URL in order to access the media
     *
     * @param url indicates the media url
     */
    fun setUrl(url: String) {
        this.mUrl = url
    }

    /**
     * Initializes the [ExoUtil.exoPlayer]
     */
    private fun initializePlayer() {
        simpleExoPlayer = exoPlayer.get()
        mPlayerView!!.setPlayer(simpleExoPlayer)
        mPlayerView!!.setPlaybackPreparer(this)
        simpleExoPlayer!!.addListener(this)
        simpleExoPlayer!!.playWhenReady = mShouldAutoPlay

        if (mUrl != null) {
            simpleExoPlayer!!.prepare(buildMediaSource(Uri.parse(mUrl)))
            simpleExoPlayer!!.seekTo(preferenceUtil.getLongData(Constants.CURRENT_POSITION_KEY, 0))
        }
    }

    /**
     * Releases the [ExoUtil.exoPlayer]
     */
    private fun releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer!!.stop()
            simpleExoPlayer!!.release()
            simpleExoPlayer!!.removeListener(this)
            mShouldAutoPlay = simpleExoPlayer!!.playWhenReady
            preferenceUtil.putLongData(Constants.CURRENT_POSITION_KEY, simpleExoPlayer!!.currentPosition)
            simpleExoPlayer = null
        }
    }

    /**
     * Gets called when Exo prepares its playback
     */
    override fun preparePlayback() {
        initializePlayer()
    }

    /**
     * Gets called when there is an error to play video
     *
     * @param error represents an error
     */
    override fun onPlayerError(error: ExoPlaybackException?) {
        mPlayerStateListener!!.onPlayerError()
    }

    /**
     * Gets called when video is ready to be played.
     *
     * @param playWhenReady indicates whether or not video is ready to be played
     * @param playbackState indicates the status of video
     */
    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        mPlayerStateListener!!.onPlayerStateChanged(playbackState)
    }

    /**
     * Clears references
     */
    fun onStart() {
        if (Util.SDK_INT > 23) {
            initializePlayer()
            if (mPlayerView != null) {
                mPlayerView!!.onResume()
            }
        }
    }

    /**
     * Regains references
     */
    fun onResume() {
        if (Util.SDK_INT <= 23 || mPlayerView == null) {
            initializePlayer()
            if (mPlayerView != null) {
                mPlayerView!!.onResume()
            }
        }
    }

    /**
     * Clears references
     */
    fun onPause() {
        if (Util.SDK_INT <= 23) {
            if (mPlayerView != null) {
                mPlayerView!!.onPause()
            }
            releasePlayer()
        }
    }

    /**
     * Regains references
     */
    fun onStop() {
        if (Util.SDK_INT > 23) {
            if (mPlayerView != null) {
                mPlayerView!!.onPause()
            }
            releasePlayer()
        }
    }

    /**
     * A listener that handles media playback
     */
    interface PlayerStateListener {

        /**
         * Gets called playback state has been changed
         *
         * @param playbackState indicates the playback state
         */
        fun onPlayerStateChanged(playbackState: Int)

        /**
         * Gets called when there is an error to play media
         */
        fun onPlayerError()
    }
}
