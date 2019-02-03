package com.nuhkoca.myapplication.ui.video;

import android.os.Bundle;
import android.view.View;

import com.google.android.exoplayer2.Player;
import com.nuhkoca.myapplication.R;
import com.nuhkoca.myapplication.databinding.ActivityVideoBinding;
import com.nuhkoca.myapplication.helper.Constants;
import com.nuhkoca.myapplication.util.exo.ExoUtil;
import com.nuhkoca.myapplication.util.exo.ExoUtilFactory;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * A {@link DaggerAppCompatActivity} that handles media playing
 *
 * @author nuhkoca
 */
public class VideoActivity extends DaggerAppCompatActivity implements ExoUtil.PlayerStateListener {

    private ActivityVideoBinding mActivityVideoBinding;
    private ExoUtil exoUtil;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    Lazy<ExoUtilFactory> exoUtilFactory;

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
        VideoViewModel videoViewModel = ViewModelProviders.of(this, viewModelFactory).get(VideoViewModel.class);

        exoUtil = exoUtilFactory.get().getExoUtil();
        exoUtil.setPlayerView(mActivityVideoBinding.pvVideo);
        exoUtil.setListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras == null) return;
        String videoUrl = extras.getString(Constants.VIDEO_KEY);

        if (videoUrl != null) {
            videoViewModel.getPlayableContent(videoUrl);
            videoViewModel.getContent().observe(this, playerResponse -> {
                if (playerResponse == null || playerResponse.getRequest().getFiles().getProgressive() == null)
                    return;
                exoUtil.setUrl(playerResponse.getRequest().getFiles().getProgressive().get(2).getUrl());
                exoUtil.onStart(); /* First, initialize when url is fetched, after that ExoUtil will handle the situation */
            });
        }
    }

    /**
     * Gets called when there is an error to play video
     */
    @Override
    public void onPlayerError() {
        mActivityVideoBinding.pbError.setVisibility(View.GONE);
    }

    /**
     * Gets called when video is ready to be played.
     *
     * @param playbackState indicates the status of video
     */
    @Override
    public void onPlayerStateChanged(int playbackState) {
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
        exoUtil.onStart();
    }

    /**
     * Regains references
     */
    @Override
    public void onResume() {
        super.onResume();
        exoUtil.onResume();
    }

    /**
     * Clears references
     */
    @Override
    public void onPause() {
        super.onPause();
        exoUtil.onPause();
    }

    /**
     * Regains references
     */
    @Override
    public void onStop() {
        super.onStop();
        exoUtil.onStop();
    }
}
