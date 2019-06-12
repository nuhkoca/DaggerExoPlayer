package com.nuhkoca.myapplication.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.nuhkoca.myapplication.R
import com.nuhkoca.myapplication.databinding.ActivityMainBinding
import com.nuhkoca.myapplication.helper.Constants
import com.nuhkoca.myapplication.helper.RecyclerViewItemDecoration
import com.nuhkoca.myapplication.ui.main.adapter.VideoAdapter
import com.nuhkoca.myapplication.ui.video.VideoActivity
import com.nuhkoca.myapplication.util.PreferenceUtil
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * A [DaggerAppCompatActivity] that handles video result listing
 *
 * @author nuhkoca
 */
class MainActivity : DaggerAppCompatActivity(), VideoAdapter.VideoItemListener, VideoAdapter.NetworkItemListener {

    private lateinit var mActivityMainBinding: ActivityMainBinding
    private lateinit var mMainViewModel: MainViewModel
    private val videoAdapter by lazy { VideoAdapter(this, this) }

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    internal lateinit var preferenceUtil: PreferenceUtil

    /**
     * Initializes the activity
     *
     * @param savedInstanceState that indicates whether or not there is
     * an instances has been saved
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mMainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        mActivityMainBinding.apply {
            rvList.addItemDecoration(
                RecyclerViewItemDecoration(applicationContext, 1, 0)
            )
            viewmodel = mMainViewModel
            lifecycleOwner = this@MainActivity
        }
        initVideoList()
    }

    /**
     * Initializes the necessary component for listing
     */
    private fun initVideoList() {
        mMainViewModel.apply {
            networkState.observe(this@MainActivity, Observer(videoAdapter::setNetworkState))
            videoResult.observe(this@MainActivity, Observer(videoAdapter::submitList))
            mActivityMainBinding.rvList.adapter = videoAdapter
        }
    }

    /**
     * Gets called when a click event has been triggered for any of video item
     *
     * @param videoId indicates the video id of the selected item
     */
    override fun onVideoItemClicked(videoId: String?) {
        preferenceUtil.putLongData(Constants.CURRENT_POSITION_KEY, 0)
        Intent(this, VideoActivity::class.java).apply {
            val extras = Bundle()
            extras.putString(Constants.VIDEO_KEY, videoId)
            putExtras(extras)
            startActivity(this)
        }
    }

    /**
     * Gets called when a click event has been triggered for any of network item
     */
    override fun onNetworkItemClicked() {
        //No need for now
    }
}
