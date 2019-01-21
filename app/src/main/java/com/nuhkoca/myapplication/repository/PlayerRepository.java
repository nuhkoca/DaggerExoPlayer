package com.nuhkoca.myapplication.repository;


import com.nuhkoca.myapplication.api.IExoAPI;
import com.nuhkoca.myapplication.data.remote.player.PlayerResponse;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A repository layer that handles playable content
 *
 * @author nuhkoca
 */
public class PlayerRepository {

    private IExoAPI iExoAPI;

    /**
     * A default constructor that gets dependencies
     *
     * @param iExoAPI represents an instance of {@link IExoAPI}
     */
    @Inject
    PlayerRepository(@Named("playerService") IExoAPI iExoAPI) {
        this.iExoAPI = iExoAPI;
    }

    /**
     * Returns video result
     *
     * @param videoId indicates the video id
     * @return video result
     */
    public Single<PlayerResponse> getPlayableContent(@NonNull String videoId) {
        return iExoAPI.getPlayableContent(videoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
