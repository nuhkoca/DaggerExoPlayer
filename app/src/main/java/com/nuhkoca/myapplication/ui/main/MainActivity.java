package com.nuhkoca.myapplication.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nuhkoca.myapplication.R;
import com.nuhkoca.myapplication.api.NetworkState;
import com.nuhkoca.myapplication.databinding.ActivityMainBinding;
import com.nuhkoca.myapplication.helper.Constants;
import com.nuhkoca.myapplication.helper.RecyclerViewItemDecoration;
import com.nuhkoca.myapplication.ui.main.adapter.VideoAdapter;
import com.nuhkoca.myapplication.ui.video.VideoActivity;
import com.nuhkoca.myapplication.util.PreferenceUtil;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * A {@link DaggerAppCompatActivity} that handles video result listing
 *
 * @author nuhkoca
 */
public class MainActivity extends DaggerAppCompatActivity {

    private ActivityMainBinding mActivityMainBinding;
    private MainViewModel mMainViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    PreferenceUtil preferenceUtil;

    /**
     * Initializes the activity
     *
     * @param savedInstanceState that indicates whether or not there is
     *                           an instances has been saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        initVideoList();
    }

    /**
     * Initializes the necessary component for listing
     */
    private void initVideoList() {
        VideoAdapter videoAdapter = new VideoAdapter(this::onVideoItemClicked, this::onNetworkItemClicked);
        mActivityMainBinding.rvList.addItemDecoration(
                new RecyclerViewItemDecoration(mActivityMainBinding.pbLoading.getContext(), 1, 0));
        mMainViewModel.getInitialLoading().observe(this, networkState -> {
            if (networkState == null) return;
            if (networkState.getStatus() == NetworkState.Status.SUCCESS) {
                mActivityMainBinding.pbLoading.setVisibility(View.GONE);
            }
        });
        mMainViewModel.getNetworkState().observe(this, networkState -> {
            if (networkState == null) return;
            videoAdapter.setNetworkState(networkState);
        });
        mMainViewModel.getVideoResult().observe(this, videoAdapter::submitList);
        mActivityMainBinding.rvList.setAdapter(videoAdapter);
    }

    /**
     * Gets called when a click event has been triggered for any of video item
     *
     * @param videoId indicates the video id of the selected item
     */
    private void onVideoItemClicked(@Nullable String videoId) {
        preferenceUtil.putLongData(Constants.CURRENT_POSITION_KEY, 0);
        Intent videoIntent = new Intent(this, VideoActivity.class);
        Bundle extras = new Bundle();
        extras.putString(Constants.VIDEO_KEY, videoId);
        videoIntent.putExtras(extras);
        startActivity(videoIntent);
    }

    /**
     * Gets called when a click event has been triggered for any of network item
     */
    private void onNetworkItemClicked() {
        //No need now
    }
}
