package com.nuhkoca.myapplication.paging;


import com.nuhkoca.myapplication.data.remote.video.VideoResponse;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

/**
 * A factory class for {@link PageKeyedVideosDataSource}
 *
 * @author nuhkoca
 */
public class VideoResultDataSourceFactory extends DataSource.Factory<Integer, VideoResponse> {

    private MutableLiveData<PageKeyedVideosDataSource> mPageKeyedVideosDataSourceMutableLiveData;
    private PageKeyedVideosDataSource pageKeyedVideosDataSource;

    /**
     * A default constructor that injects required dependencies
     *
     * @param pageKeyedVideosDataSource represents an instance {@link PageKeyedVideosDataSource}
     */
    @Inject
    VideoResultDataSourceFactory(PageKeyedVideosDataSource pageKeyedVideosDataSource) {
        this.mPageKeyedVideosDataSourceMutableLiveData = new MutableLiveData<>();
        this.pageKeyedVideosDataSource = pageKeyedVideosDataSource;
    }

    /**
     * Applies the given function to each value emitted by DataSources produced by this Factory.
     *
     * @return a {@link DataSource}
     */
    @NonNull
    @Override
    public DataSource<Integer, VideoResponse> create() {
        mPageKeyedVideosDataSourceMutableLiveData.postValue(pageKeyedVideosDataSource);
        return pageKeyedVideosDataSource;
    }

    /**
     * Returns {@link VideoResultDataSourceFactory#mPageKeyedVideosDataSourceMutableLiveData}
     *
     * @return {@link VideoResultDataSourceFactory#mPageKeyedVideosDataSourceMutableLiveData}
     */
    public MutableLiveData<PageKeyedVideosDataSource> getPageKeyedVideosDataSourceMutableLiveData() {
        return mPageKeyedVideosDataSourceMutableLiveData;
    }

    /**
     * Returns {@link VideoResultDataSourceFactory#pageKeyedVideosDataSource}
     *
     * @return {@link VideoResultDataSourceFactory#pageKeyedVideosDataSource}
     */
    public PageKeyedVideosDataSource getPageKeyedVideosDataSource() {
        return pageKeyedVideosDataSource;
    }
}
