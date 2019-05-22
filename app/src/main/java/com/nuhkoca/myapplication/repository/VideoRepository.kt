package com.nuhkoca.myapplication.repository

import android.content.Context
import com.nuhkoca.myapplication.api.IExoAPI
import com.nuhkoca.myapplication.base.BaseRepository
import com.nuhkoca.myapplication.data.remote.video.VideoWrapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

/**
 * A repository layer that handles video searching
 *
 * @author nuhkoca
 */
class VideoRepository
/**
 * A default constructor that gets dependencies
 *
 * @param iExoAPI represents an instance of [IExoAPI]
 * @param context represents an instance of [Context]
 */
@Inject
internal constructor(@Named("videoService") iExoAPI: IExoAPI, context: Context) : BaseRepository(iExoAPI, context) {

    /**
     * Returns video result
     *
     * @param page indicates the page number
     * @return video result
     */
    fun getVideoList(page: Int): Single<Response<VideoWrapper>> {
        return iExoAPIService.getVideoList(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { interceptError(it) }
    }
}
