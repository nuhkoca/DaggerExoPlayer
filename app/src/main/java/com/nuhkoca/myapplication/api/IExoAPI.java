package com.nuhkoca.myapplication.api;

import com.nuhkoca.myapplication.data.remote.player.PlayerResponse;
import com.nuhkoca.myapplication.data.remote.video.VideoWrapper;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The main services that handles all endpoint processes
 *
 * @author nuhkoca
 */
public interface IExoAPI {

    /**
     * Returns a list of videos
     *
     * @param page indicates page number
     * @return a list of videos
     */
    @GET("videos")
    Single<VideoWrapper> getVideoList(@Query("page") int page);

    /**
     * Returns a playable content for the video id
     *
     * @param videoId indicates the video id
     * @return a list of videos
     */
    @GET("{videoId}/config")
    Single<PlayerResponse> getPlayableContent(@Path("videoId") @NonNull String videoId);
}
