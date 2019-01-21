package com.nuhkoca.myapplication.repository;


import com.nuhkoca.myapplication.api.IExoAPI;
import com.nuhkoca.myapplication.data.remote.video.VideoWrapper;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A repository layer that handles video searching
 *
 * @author nuhkoca
 */
public class VideoRepository {

    private IExoAPI iExoAPI;

    /**
     * A default constructor that gets dependencies
     *
     * @param iExoAPI represents an instance of {@link IExoAPI}
     */
    @Inject
    VideoRepository(@Named("videoService") IExoAPI iExoAPI) {
        this.iExoAPI = iExoAPI;
    }

    /**
     * Returns video result
     *
     * @param page indicates the page number
     * @return video result
     */
    public Single<VideoWrapper> getVideoList(int page) {
        return iExoAPI.getVideoList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
