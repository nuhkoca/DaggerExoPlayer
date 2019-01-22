package com.nuhkoca.myapplication.ui.video;

import com.nuhkoca.myapplication.data.remote.player.PlayerResponse;
import com.nuhkoca.myapplication.repository.PlayerRepository;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * A {@link androidx.lifecycle.ViewModel} for media playing
 *
 * @author nuhkoca
 */
public class VideoViewModel extends ViewModel {

    private PlayerRepository playerRepository;
    private CompositeDisposable compositeDisposable;

    private MutableLiveData<PlayerResponse> mPlayerResponse = new MutableLiveData<>();

    /**
     * A default constructor that inject required dependencies
     *
     * @param playerRepository    represents an instance of {@link PlayerRepository}
     * @param compositeDisposable represents an instance of {@link CompositeDisposable}
     */
    @Inject
    VideoViewModel(@NonNull PlayerRepository playerRepository,
                   @NonNull CompositeDisposable compositeDisposable) {
        this.playerRepository = playerRepository;
        this.compositeDisposable = compositeDisposable;
    }

    /**
     * Returns playable content
     *
     * @param videoId indicates the video id
     */
    void getPlayableContent(@NonNull String videoId) {
        Disposable player = playerRepository.getPlayableContent(videoId)
                .subscribe(this::onSuccess, this::onError);

        compositeDisposable.add(player);
    }

    /**
     * Gets called when the playable content is retrieved
     *
     * @param playerResponse represents an instance of {@link PlayerResponse}
     */
    private void onSuccess(@NonNull PlayerResponse playerResponse) {
        mPlayerResponse.setValue(playerResponse);
    }

    /**
     * Gets called when there is an error
     *
     * @param throwable represents any error
     */
    private void onError(@NonNull Throwable throwable) {

    }

    /**
     * Returns the playable content
     *
     * @return the playable content
     */
    MutableLiveData<PlayerResponse> getContent() {
        return mPlayerResponse;
    }

    /**
     * Clears references
     */
    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
