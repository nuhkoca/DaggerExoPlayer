package com.nuhkoca.myapplication.repository;


import android.content.Context;

import com.nuhkoca.myapplication.api.IExoAPI;
import com.nuhkoca.myapplication.base.BaseRepository;
import com.nuhkoca.myapplication.data.remote.video.VideoWrapper;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A repository layer that handles video searching
 *
 * @author nuhkoca
 */
public class VideoRepository extends BaseRepository {

    /**
     * A default constructor that gets dependencies
     *
     * @param iExoAPI represents an instance of {@link IExoAPI}
     * @param context represents an instance of {@link Context}
     */
    @Inject
    VideoRepository(@Named("videoService") @NonNull IExoAPI iExoAPI, @NonNull Context context) {
        super(iExoAPI, context);
    }

    /**
     * Returns video result
     *
     * @param page indicates the page number
     * @return video result
     */
    public Single<Response<VideoWrapper>> getVideoList(int page) {
        return getIExoAPIService().getVideoList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(this::interceptError);
    }
}
