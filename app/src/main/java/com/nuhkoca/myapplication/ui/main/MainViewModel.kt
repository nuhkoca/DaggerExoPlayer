package com.nuhkoca.myapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.nuhkoca.myapplication.api.NetworkState
import com.nuhkoca.myapplication.data.remote.video.VideoResponse
import com.nuhkoca.myapplication.helper.AppExecutors
import com.nuhkoca.myapplication.helper.Constants
import com.nuhkoca.myapplication.paging.VideoResultDataSourceFactory
import javax.inject.Inject

/**
 * A [androidx.lifecycle.ViewModel] for video result
 *
 * @author nuhkoca
 */
class MainViewModel
/**
 * A default constructor that inject required dependencies
 *
 * @param videoResultDataSourceFactory represents an instance of [VideoResultDataSourceFactory]
 * @param appExecutors                 represents an instance of [AppExecutors]
 */
@Inject
internal constructor(private val videoResultDataSourceFactory: VideoResultDataSourceFactory,
                     private val appExecutors: AppExecutors) : ViewModel() {

    /**
     * Returns initial network state
     *
     * @return initial network state
     */
    var initialLoading: LiveData<NetworkState>? = null
        private set
    /**
     * Returns network state
     *
     * @return network state
     */
    internal var networkState: LiveData<NetworkState>? = null
        private set
    /**
     * Returns video result
     *
     * @return video result
     */
    internal var videoResult: LiveData<PagedList<VideoResponse>>? = null
        private set

    init {
        getVideoList()
    }

    /**
     * Retrieves video result
     */
    private fun getVideoList() {
        networkState = Transformations.switchMap(videoResultDataSourceFactory.pageKeyedVideosDataSourceMutableLiveData) { it.networkState }

        initialLoading = Transformations.switchMap(videoResultDataSourceFactory.pageKeyedVideosDataSourceMutableLiveData) { it.initialLoading }

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(Constants.INITIAL_LOAD_SIZE_HINT) //first load
                .setPrefetchDistance(Constants.INITIAL_LOAD_SIZE_HINT)
                .setPageSize(Constants.OFFSET_SIZE) //offset
                .build()

        videoResult = LivePagedListBuilder(videoResultDataSourceFactory, config)
                .setFetchExecutor(appExecutors.networkIO())
                .build()
    }

    /**
     * Clears references
     */
    override fun onCleared() {
        videoResultDataSourceFactory.pageKeyedVideosDataSource.clear()
        super.onCleared()
    }
}
