package com.nuhkoca.myapplication.util.exo;

import android.net.Uri;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;
import com.nuhkoca.myapplication.helper.Constants;
import com.nuhkoca.myapplication.util.PreferenceUtil;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A utility class to conduct Exo implementation
 *
 * @author nuhkoca
 */
public class ExoUtil implements PlaybackPreparer, Player.EventListener {

    private SimpleExoPlayer simpleExoPlayer;
    private Provider<SimpleExoPlayer> exoPlayer;
    private ExtractorMediaSource.Factory factory;
    private PreferenceUtil preferenceUtil;

    private PlayerStateListener mPlayerStateListener;
    private PlayerView mPlayerView;

    private boolean mShouldAutoPlay;
    private String mUrl;

    /**
     * A default constructor that injects dependencies
     *
     * @param exoPlayer      represents an instance of {@link SimpleExoPlayer}
     * @param factory        represents an instance of {@link ExtractorMediaSource.Factory}
     * @param preferenceUtil represents an instance of {@link PreferenceUtil}
     */
    @Inject
    ExoUtil(@NonNull Provider<SimpleExoPlayer> exoPlayer,
            @NonNull ExtractorMediaSource.Factory factory,
            @NonNull PreferenceUtil preferenceUtil) {
        this.exoPlayer = exoPlayer;
        this.factory = factory;
        this.preferenceUtil = preferenceUtil;

        mShouldAutoPlay = true;
    }

    /**
     * Helps build a {@link ExtractorMediaSource.Factory}
     *
     * @param uri represents a url to be played
     * @return an instance of {@link MediaSource}
     */
    @NonNull
    private MediaSource buildMediaSource(@Nullable Uri uri) {
        return factory.createMediaSource(uri);
    }

    public void setListener(@NonNull PlayerStateListener playerStateListener) {
        this.mPlayerStateListener = playerStateListener;
    }

    /**
     * Helps set a {@link PlayerView} in order to play media
     *
     * @param playerView represents a {@link PlayerView}
     */
    public void setPlayerView(@NonNull PlayerView playerView) {
        this.mPlayerView = playerView;
    }

    /**
     * Helps set a URL in order to access the media
     *
     * @param url indicates the media url
     */
    public void setUrl(@NonNull String url) {
        this.mUrl = url;
    }

    /**
     * Initializes the {@link ExoUtil#exoPlayer}
     */
    private void initializePlayer() {
        simpleExoPlayer = exoPlayer.get();
        mPlayerView.setPlayer(simpleExoPlayer);
        mPlayerView.setPlaybackPreparer(this);
        simpleExoPlayer.addListener(this);
        simpleExoPlayer.setPlayWhenReady(mShouldAutoPlay);

        if (mUrl != null) {
            simpleExoPlayer.prepare(buildMediaSource(Uri.parse(mUrl)));
            simpleExoPlayer.seekTo(preferenceUtil.getLongData(Constants.CURRENT_POSITION_KEY, 0));
        }
    }

    /**
     * Releases the {@link ExoUtil#exoPlayer}
     */
    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer.removeListener(this);
            mShouldAutoPlay = simpleExoPlayer.getPlayWhenReady();
            preferenceUtil.putLongData(Constants.CURRENT_POSITION_KEY, simpleExoPlayer.getCurrentPosition());
            simpleExoPlayer = null;
        }
    }

    /**
     * Gets called when Exo prepares its playback
     */
    @Override
    public void preparePlayback() {
        initializePlayer();
    }

    /**
     * Gets called when there is an error to play video
     *
     * @param error represents an error
     */
    @Override
    public void onPlayerError(ExoPlaybackException error) {
        mPlayerStateListener.onPlayerError();
    }

    /**
     * Gets called when video is ready to be played.
     *
     * @param playWhenReady indicates whether or not video is ready to be played
     * @param playbackState indicates the status of video
     */
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        mPlayerStateListener.onPlayerStateChanged(playbackState);
    }

    /**
     * Clears references
     */
    public void onStart() {
        if (Util.SDK_INT > 23) {
            initializePlayer();
            if (mPlayerView != null) {
                mPlayerView.onResume();
            }
        }
    }

    /**
     * Regains references
     */
    public void onResume() {
        if (Util.SDK_INT <= 23 || mPlayerView == null) {
            initializePlayer();
            if (mPlayerView != null) {
                mPlayerView.onResume();
            }
        }
    }

    /**
     * Clears references
     */
    public void onPause() {
        if (Util.SDK_INT <= 23) {
            if (mPlayerView != null) {
                mPlayerView.onPause();
            }
            releasePlayer();
        }
    }

    /**
     * Regains references
     */
    public void onStop() {
        if (Util.SDK_INT > 23) {
            if (mPlayerView != null) {
                mPlayerView.onPause();
            }
            releasePlayer();
        }
    }

    /**
     * A listener that handles media playback
     */
    public interface PlayerStateListener {

        /**
         * Gets called playback state has been changed
         *
         * @param playbackState indicates the playback state
         */
        void onPlayerStateChanged(int playbackState);

        /**
         * Gets called when there is an error to play media
         */
        void onPlayerError();
    }
}
