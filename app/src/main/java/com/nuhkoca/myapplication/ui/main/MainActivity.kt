package com.nuhkoca.myapplication.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nuhkoca.myapplication.R
import com.nuhkoca.myapplication.databinding.ActivityMainBinding
import com.nuhkoca.myapplication.helper.Constants
import com.nuhkoca.myapplication.helper.RecyclerViewItemDecoration
import com.nuhkoca.myapplication.ui.main.adapter.VideoAdapter
import com.nuhkoca.myapplication.ui.video.VideoActivity
import com.nuhkoca.myapplication.util.PreferenceUtil
import com.nuhkoca.myapplication.util.ext.get
import com.nuhkoca.myapplication.util.ext.launchActivityWithExtra
import com.nuhkoca.myapplication.util.ext.observeWith
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * A [DaggerAppCompatActivity] that handles video result listing
 *
 * @author nuhkoca
 */
class MainActivity : DaggerAppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mainViewModel: MainViewModel

    @Inject
    internal lateinit var preferenceUtil: PreferenceUtil

    @Inject
    internal lateinit var videoAdapter: VideoAdapter

    private lateinit var mActivityMainBinding: ActivityMainBinding

    /**
     * Initializes the activity
     *
     * @param savedInstanceState that indicates whether or not there is an instances has been saved
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = viewModelFactory.get(this)
        mActivityMainBinding.apply {
            rvList.addItemDecoration(RecyclerViewItemDecoration(applicationContext))
            viewmodel = mainViewModel
            lifecycleOwner = this@MainActivity
            mActivityMainBinding.rvList.adapter = videoAdapter
        }
        observeViewModel()
        observeVideoClicks()
    }

    /**
     * Initializes the necessary component for listing
     */
    private fun observeViewModel() = with(mainViewModel) {
        networkState.observeWith(this@MainActivity, (videoAdapter::setNetworkState))
        videoResult.observeWith(this@MainActivity, (videoAdapter::submitList))
    }

    /**
     * Observes video item clicks
     */
    private fun observeVideoClicks() {
        videoAdapter.videoItemClick.observeWith(this) {
            preferenceUtil.putLongData(Constants.CURRENT_POSITION_KEY, 0)
            launchActivityWithExtra<VideoActivity> { putString(Constants.VIDEO_KEY, it) }
        }
    }
}
