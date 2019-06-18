package com.nuhkoca.myapplication.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nuhkoca.myapplication.data.remote.video.VideoResponse
import javax.inject.Inject

/**
 * A factory class for [PageKeyedVideosDataSource]
 *
 * @author nuhkoca
 */
class VideoResultDataSourceFactory @Inject constructor(
    private val pageKeyedVideosDataSource: PageKeyedVideosDataSource
) : DataSource.Factory<Int, VideoResponse>() {

    /**
     * Returns [VideoResultDataSourceFactory.mPageKeyedVideosDataSourceMutableLiveData]
     *
     * @return [VideoResultDataSourceFactory.mPageKeyedVideosDataSourceMutableLiveData]
     */
    val pageKeyedVideosDataSourceMutableLiveData: MutableLiveData<PageKeyedVideosDataSource> = MutableLiveData()

    /**
     * Applies the given function to each value emitted by DataSources produced by this Factory.
     *
     * @return a [DataSource]
     */
    override fun create(): DataSource<Int, VideoResponse> {
        pageKeyedVideosDataSourceMutableLiveData.postValue(pageKeyedVideosDataSource)
        return pageKeyedVideosDataSource
    }
}
