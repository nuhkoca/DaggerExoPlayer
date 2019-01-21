package com.nuhkoca.myapplication.ui.main;

import com.nuhkoca.myapplication.api.NetworkState;
import com.nuhkoca.myapplication.data.remote.video.VideoResponse;
import com.nuhkoca.myapplication.helper.AppExecutors;
import com.nuhkoca.myapplication.helper.Constants;
import com.nuhkoca.myapplication.paging.PageKeyedVideosDataSource;
import com.nuhkoca.myapplication.paging.VideoResultDataSourceFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * A {@link androidx.lifecycle.ViewModel} for video result
 *
 * @author nuhkoca
 */
public class MainViewModel extends ViewModel {

    private LiveData<NetworkState> mInitialLoading;
    private LiveData<NetworkState> mNetworkState;
    private LiveData<PagedList<VideoResponse>> mVideoResult;

    private VideoResultDataSourceFactory videoResultDataSourceFactory;
    private AppExecutors appExecutors;

    /**
     * A default constructor that inject required dependencies
     *
     * @param videoResultDataSourceFactory represents an instance of {@link VideoResultDataSourceFactory}
     * @param appExecutors                 represents an instance of {@link AppExecutors}
     */
    @Inject
    MainViewModel(@NonNull VideoResultDataSourceFactory videoResultDataSourceFactory,
                  @NonNull AppExecutors appExecutors) {
        this.videoResultDataSourceFactory = videoResultDataSourceFactory;
        this.appExecutors = appExecutors;
        getVideoList();
    }

    /**
     * Retrieves video result
     */
    private void getVideoList() {
        mNetworkState = Transformations.switchMap(videoResultDataSourceFactory.getPageKeyedVideosDataSourceMutableLiveData(), PageKeyedVideosDataSource::getNetworkState);

        mInitialLoading = Transformations.switchMap(videoResultDataSourceFactory.getPageKeyedVideosDataSourceMutableLiveData(),
                PageKeyedVideosDataSource::getInitialLoading);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(Constants.INITIAL_LOAD_SIZE_HINT) //first load
                .setPrefetchDistance(Constants.INITIAL_LOAD_SIZE_HINT)
                .setPageSize(Constants.OFFSET_SIZE) //offset
                .build();

        mVideoResult = new LivePagedListBuilder<>(videoResultDataSourceFactory, config)
                .setFetchExecutor(appExecutors.networkIO())
                .build();
    }

    /**
     * Returns video result
     *
     * @return video result
     */
    public LiveData<PagedList<VideoResponse>> getVideoResult() {
        return mVideoResult;
    }

    /**
     * Returns network state
     *
     * @return network state
     */
    public LiveData<NetworkState> getNetworkState() {
        return mNetworkState;
    }

    /**
     * Returns initial network state
     *
     * @return initial network state
     */
    public LiveData<NetworkState> getInitialLoading() {
        return mInitialLoading;
    }
}
