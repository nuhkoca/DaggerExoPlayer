package com.nuhkoca.myapplication.paging

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.nuhkoca.myapplication.R
import com.nuhkoca.myapplication.api.NetworkState
import com.nuhkoca.myapplication.data.remote.video.VideoResponse
import com.nuhkoca.myapplication.data.remote.video.VideoWrapper
import com.nuhkoca.myapplication.repository.VideoRepository
import io.reactivex.disposables.CompositeDisposable
import java.util.ArrayList
import javax.inject.Inject

/**
 * A [PageKeyedDataSource] class that handles pagination with the API.
 *
 * @author nuhkoca
 */
class PageKeyedVideosDataSource @Inject internal constructor(
    private val videoRepository: VideoRepository,
    private val context: Context,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, VideoResponse>(), IPaginationCallback<VideoWrapper, VideoResponse> {

    /**
     * Returns network state
     *
     * @return network state
     */
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()
    /**
     * Returns initial network state
     *
     * @return initial network state
     */
    val initialLoading: MutableLiveData<NetworkState> = MutableLiveData()

    /**
     * Gets called when first network call
     *
     * @param params   holder object for inputs to [.loadInitial].
     * @param callback * Callback for [.loadInitial]
     * to return data and, optionally, position/count information.
     */
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, VideoResponse>) {
        val videoResponses = ArrayList<VideoResponse>()

        networkState.postValue(NetworkState.LOADING)
        initialLoading.postValue(NetworkState.LOADING)

        val artist = videoRepository.getVideoList(1)
            .subscribe(
                { videoWrapper -> onInitialSuccess(videoWrapper.body()!!, callback, videoResponses) },
                ::onInitialError
            )

        compositeDisposable.add(artist)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, VideoResponse>) {

    }

    /**
     * Gets called when next network calls
     *
     * @param params   holder object for inputs to [.loadAfter] (androidx.paging.PageKeyedDataSource.LoadParams, LoadInitialCallback)}.
     * @param callback * Callback for [.loadAfter] (LoadParams, LoadCallback)}
     * to return data and, optionally, position/count information.
     */
    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, VideoResponse>
    ) {
        val videoResponses = ArrayList<VideoResponse>()

        networkState.postValue(NetworkState.LOADING)

        val artist = videoRepository.getVideoList(params.key)
            .subscribe(
                { videoWrapper -> onPaginationSuccess(videoWrapper.body()!!, callback, params, videoResponses) },
                ::onPaginationError
            )

        compositeDisposable.add(artist)
    }

    /**
     * Gets called when there is any error at first fetch
     *
     * @param throwable represents any error
     */
    override fun onInitialError(throwable: Throwable) {
        networkState.postValue(NetworkState(NetworkState.Status.FAILED, throwable.message))
        initialLoading.postValue(NetworkState(NetworkState.Status.FAILED, throwable.message))
    }

    /**
     * Gets called when first fetch is successful
     *
     * @param videoWrapper  represents the most top model
     * @param callback represents [LoadInitialCallback]
     * @param model    represents the actual response
     */
    override fun onInitialSuccess(
        wrapper: VideoWrapper,
        callback: LoadInitialCallback<Int, VideoResponse>,
        model: MutableList<VideoResponse>
    ) {
        if (wrapper.data == null || wrapper.data.size == 0) {
            initialLoading.postValue(
                NetworkState(
                    NetworkState.Status.NO_ITEM,
                    context.getString(R.string.network_call_empty_response)
                )
            )
            networkState.postValue(
                NetworkState(
                    NetworkState.Status.NO_ITEM,
                    context.getString(R.string.network_call_empty_response)
                )
            )
            return
        }

        model.addAll(wrapper.data)

        val nextKey = if (wrapper.paging.next == null) null else 1
        callback.onResult(model, null, nextKey)

        networkState.postValue(NetworkState.LOADED)
        initialLoading.postValue(NetworkState.LOADED)
    }

    /**
     * Gets called when there is any error at next fetches
     *
     * @param throwable represents any error
     */
    override fun onPaginationError(throwable: Throwable) {
        networkState.postValue(
            NetworkState(
                NetworkState.Status.FAILED,
                context.getString(R.string.network_call_initial_error)
            )
        )
    }

    /**
     * Gets called when next fetches are successful
     *
     * @param videoWrapper  represents the most top model
     * @param callback represents [LoadInitialCallback]
     * @param model    represents the actual response
     */
    override fun onPaginationSuccess(
        wrapper: VideoWrapper,
        callback: LoadCallback<Int, VideoResponse>,
        params: LoadParams<Int>,
        model: MutableList<VideoResponse>
    ) {
        if (wrapper.data == null || wrapper.data.size == 0) {
            networkState.postValue(
                NetworkState(
                    NetworkState.Status.NO_ITEM,
                    context.getString(R.string.network_call_empty_response)
                )
            )
            return
        }

        model.addAll(wrapper.data)
        val nextKey = if (wrapper.paging.next == null) null else params.key + 1

        callback.onResult(model, nextKey)

        networkState.postValue(NetworkState.LOADED)
    }

    /**
     * Clears references
     */
    override fun invalidate() {
        super.invalidate()
        compositeDisposable.clear()
    }
}
