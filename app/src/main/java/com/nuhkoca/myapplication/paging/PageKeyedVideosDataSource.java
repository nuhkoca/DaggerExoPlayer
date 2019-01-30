package com.nuhkoca.myapplication.paging;

import android.content.Context;

import com.nuhkoca.myapplication.R;
import com.nuhkoca.myapplication.api.NetworkState;
import com.nuhkoca.myapplication.data.remote.video.VideoResponse;
import com.nuhkoca.myapplication.data.remote.video.VideoWrapper;
import com.nuhkoca.myapplication.repository.VideoRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * A {@link PageKeyedDataSource} class that handles pagination with the API.
 *
 * @author nuhkoca
 */
public class PageKeyedVideosDataSource extends PageKeyedDataSource<Integer, VideoResponse> implements IPaginationCallback<VideoWrapper, VideoResponse> {

    private VideoRepository videoRepository;
    private Context context;

    private MutableLiveData<NetworkState> mNetworkState;
    private MutableLiveData<NetworkState> mInitialLoading;

    private CompositeDisposable compositeDisposable;

    /**
     * A default constructor that inject required dependencies.
     *
     * @param videoRepository represents an instance of {@link VideoRepository}
     * @param context          represents an instance of {@link Context}
     */
    @Inject
    PageKeyedVideosDataSource(@NonNull VideoRepository videoRepository,
                              @NonNull Context context) {
        this.videoRepository = videoRepository;
        this.context = context;

        mNetworkState = new MutableLiveData<>();
        mInitialLoading = new MutableLiveData<>();

        compositeDisposable = new CompositeDisposable();
    }

    /**
     * Returns network state
     *
     * @return network state
     */
    public MutableLiveData<NetworkState> getNetworkState() {
        return mNetworkState;
    }

    /**
     * Returns initial network state
     *
     * @return initial network state
     */
    public MutableLiveData<NetworkState> getInitialLoading() {
        return mInitialLoading;
    }

    /**
     * Gets called when first network call
     *
     * @param params   holder object for inputs to {@link #loadInitial(LoadInitialParams, LoadInitialCallback)}.
     * @param callback * Callback for {@link #loadInitial(LoadInitialParams, LoadInitialCallback)}
     *                 to return data and, optionally, position/count information.
     */
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, VideoResponse> callback) {
        List<VideoResponse> videoResponses = new ArrayList<>();

        mNetworkState.postValue(NetworkState.LOADING);
        mInitialLoading.postValue(NetworkState.LOADING);

        Disposable artist = videoRepository.getVideoList(1)
                .subscribe(videoWrapper -> onInitialSuccess(videoWrapper.body(), callback, videoResponses), this::onInitialError);

        compositeDisposable.add(artist);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, VideoResponse> callback) {

    }

    /**
     * Gets called when next network calls
     *
     * @param params   holder object for inputs to {@link #loadAfter(LoadParams, LoadCallback)} (androidx.paging.PageKeyedDataSource.LoadParams, LoadInitialCallback)}.
     * @param callback * Callback for {@link #loadAfter(LoadParams, LoadCallback)} (LoadParams, LoadCallback)}
     *                 to return data and, optionally, position/count information.
     */
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, VideoResponse> callback) {
        List<VideoResponse> videoResponses = new ArrayList<>();

        mNetworkState.postValue(NetworkState.LOADING);

        Disposable artist = videoRepository.getVideoList(params.key)
                .subscribe(videoWrapper -> onPaginationSuccess(videoWrapper.body(), callback, params, videoResponses), this::onPaginationError);

        compositeDisposable.add(artist);
    }

    /**
     * Gets called when there is any error at first fetch
     *
     * @param throwable represents any error
     */
    @Override
    public void onInitialError(Throwable throwable) {
        mNetworkState.postValue(new NetworkState(NetworkState.Status.FAILED, throwable.getMessage()));
        mInitialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, throwable.getMessage()));
    }

    /**
     * Gets called when first fetch is successful
     *
     * @param videoWrapper  represents the most top model
     * @param callback represents {@link LoadInitialCallback}
     * @param model    represents the actual response
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onInitialSuccess(VideoWrapper videoWrapper, LoadInitialCallback<Integer, VideoResponse> callback, List<VideoResponse> model) {
        if (videoWrapper.getData() == null
                || videoWrapper.getData().size() == 0) {
            mInitialLoading.postValue(new NetworkState(NetworkState.Status.NO_ITEM, context.getString(R.string.network_call_empty_response)));
            mNetworkState.postValue(new NetworkState(NetworkState.Status.NO_ITEM, context.getString(R.string.network_call_empty_response)));
            return;
        }

        model.addAll(videoWrapper.getData());

        int nextKey = (videoWrapper.getPaging().getNext() == null) ? null : 1;
        callback.onResult(model, null, nextKey);

        mNetworkState.postValue(NetworkState.LOADED);
        mInitialLoading.postValue(NetworkState.LOADED);
    }

    /**
     * Gets called when there is any error at next fetches
     *
     * @param throwable represents any error
     */
    @Override
    public void onPaginationError(Throwable throwable) {
        mNetworkState.postValue(new NetworkState(NetworkState.Status.FAILED, context.getString(R.string.network_call_initial_error)));
    }

    /**
     * Gets called when next fetches are successful
     *
     * @param videoWrapper  represents the most top model
     * @param callback represents {@link LoadInitialCallback}
     * @param model    represents the actual response
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onPaginationSuccess(VideoWrapper videoWrapper, LoadCallback<Integer, VideoResponse> callback, LoadParams<Integer> params, List<VideoResponse> model) {
        if (videoWrapper.getData() == null
                || videoWrapper.getData().size() == 0) {
            mNetworkState.postValue(new NetworkState(NetworkState.Status.NO_ITEM, context.getString(R.string.network_call_empty_response)));
            return;
        }

        model.addAll(videoWrapper.getData());
        int nextKey = (videoWrapper.getPaging().getNext() == null) ? null : params.key + 1;

        callback.onResult(model, nextKey);

        mNetworkState.postValue(NetworkState.LOADED);
    }

    /**
     * Clears references
     */
    @Override
    public void clear() {
        compositeDisposable.clear();
    }
}
