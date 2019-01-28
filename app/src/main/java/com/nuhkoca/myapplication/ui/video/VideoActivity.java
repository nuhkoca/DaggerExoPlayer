package com.nuhkoca.myapplication.ui.video;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.util.Util;
import com.nuhkoca.myapplication.R;
import com.nuhkoca.myapplication.databinding.ActivityVideoBinding;
import com.nuhkoca.myapplication.helper.Constants;
import com.nuhkoca.myapplication.util.PreferenceUtil;

import java.net.CookieHandler;
import java.net.CookieManager;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * A {@link DaggerAppCompatActivity} that handles media playing
 *
 * @author nuhkoca
 */
public class VideoActivity extends DaggerAppCompatActivity implements PlaybackPreparer, Player.EventListener {

    private static boolean mShouldAutoPlay;
    private ActivityVideoBinding mActivityVideoBinding;
    private VideoViewModel mVideoViewModel;
    private String mVideoUrl;

    @Inject
    SimpleExoPlayer exoPlayer;
    @Inject
    ExtractorMediaSource.Factory factory;
    @Inject
    CookieManager DEFAULT_COOKIE_MANAGER;
    @Inject
    PreferenceUtil preferenceUtil;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    /**
     * Initializes the activity
     *
     * @param savedInstanceState that indicates whether or not there is
     *                           an instances has been saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_video);
        mVideoViewModel = ViewModelProviders.of(this, viewModelFactory).get(VideoViewModel.class);

        if (savedInstanceState == null) {
            mShouldAutoPlay = true;
        }

        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }

        Bundle extras = getIntent().getExtras();
        if (extras == null) return;
        mVideoUrl = extras.getString(Constants.VIDEO_KEY);
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

    /**
     * Initializes the {@link VideoActivity#exoPlayer}
     */
    private void initializePlayer() {
        exoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        mActivityVideoBinding.pvVideo.setPlayer(exoPlayer);
        mActivityVideoBinding.pvVideo.setPlaybackPreparer(this);
        exoPlayer.addListener(this);
        exoPlayer.setPlayWhenReady(mShouldAutoPlay);

        mVideoViewModel.getPlayableContent(mVideoUrl);
        mVideoViewModel.getContent().observe(this, playerResponse -> {
            if (playerResponse == null || playerResponse.getRequest().getFiles().getProgressive() == null)
                return;
            mVideoUrl = playerResponse.getRequest().getFiles().getProgressive().get(2).getUrl();
            exoPlayer.prepare(buildMediaSource(Uri.parse(mVideoUrl)));
            exoPlayer.seekTo(preferenceUtil.getLongData(Constants.CURRENT_POSITION_KEY, 0));
        });
    }

    /**
     * Releases the {@link VideoActivity#exoPlayer}
     */
    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            //exoPlayer.release();
            exoPlayer.removeListener(this);
            mShouldAutoPlay = exoPlayer.getPlayWhenReady();
            preferenceUtil.putLongData(Constants.CURRENT_POSITION_KEY, exoPlayer.getCurrentPosition());
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
        mActivityVideoBinding.pbError.setVisibility(View.GONE);
    }

    /**
     * Gets called when video is ready to be played.
     *
     * @param playWhenReady indicates whether or not video is ready to be played
     * @param playbackState indicates the status of video
     */
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case Player.STATE_BUFFERING:
                mActivityVideoBinding.pbError.setVisibility(View.VISIBLE);
                break;
            case Player.STATE_READY:
                mActivityVideoBinding.pbError.setVisibility(View.GONE);
                break;

            case Player.STATE_IDLE:
                mActivityVideoBinding.pbError.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * Clears references
     */
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
            if (mActivityVideoBinding.pvVideo != null) {
                mActivityVideoBinding.pvVideo.onResume();
            }
        }
    }

    /**
     * Regains references
     */
    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mActivityVideoBinding.pvVideo == null) {
            initializePlayer();
            if (mActivityVideoBinding.pvVideo != null) {
                mActivityVideoBinding.pvVideo.onResume();
            }
        }
    }

    /**
     * Clears references
     */
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (mActivityVideoBinding.pvVideo != null) {
                mActivityVideoBinding.pvVideo.onPause();
            }
            releasePlayer();
        }
    }

    /**
     * Regains references
     */
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (mActivityVideoBinding.pvVideo != null) {
                mActivityVideoBinding.pvVideo.onPause();
            }
            releasePlayer();
        }
    }
}
