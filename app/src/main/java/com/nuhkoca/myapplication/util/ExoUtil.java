package com.nuhkoca.myapplication.util;

import android.net.Uri;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

import java.net.CookieHandler;
import java.net.CookieManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@Singleton
public class ExoUtil implements PlaybackPreparer, Player.EventListener {

    private SimpleExoPlayer exoPlayer;
    private ExtractorMediaSource.Factory factory;
    private PlayerView mPlayerView;
    private boolean mShouldAutoPlay;
    private String mUrl;
    private PlayerStateListener mPlayerStateListener;

    @Inject
    ExoUtil(SimpleExoPlayer exoPlayer, ExtractorMediaSource.Factory factory, CookieManager DEFAULT_COOKIE_MANAGER) {
        this.exoPlayer = exoPlayer;
        this.factory = factory;
        mShouldAutoPlay = true;

        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }
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

    public void setPlayerView(@NonNull PlayerView playerView) {
        this.mPlayerView = playerView;
    }

    public void setUrl(@NonNull String url) {
        this.mUrl = url;
    }

    public void setListener(@NonNull PlayerStateListener playerStateListener) {
        this.mPlayerStateListener = playerStateListener;
    }

    /**
     * Initializes the {@link ExoUtil#exoPlayer}
     */
    private void initializePlayer() {
        exoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        mPlayerView.setPlayer(exoPlayer);
        mPlayerView.setPlaybackPreparer(this);
        exoPlayer.addListener(this);
        exoPlayer.setPlayWhenReady(mShouldAutoPlay);
        exoPlayer.prepare(buildMediaSource(Uri.parse(mUrl)));
    }

    /**
     * Releases the {@link ExoUtil#exoPlayer}
     */
    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            //exoPlayer.release();
            exoPlayer.removeListener(this);
            mShouldAutoPlay = exoPlayer.getPlayWhenReady();
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

    public interface PlayerStateListener {

        void onPlayerStateChanged(int playbackState);

        void onPlayerError();
    }
}
