package com.nuhkoca.myapplication.ui.video;

import com.nuhkoca.myapplication.data.remote.player.PlayerResponse;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * A {@link androidx.lifecycle.ViewModel} for media playing
 *
 * @author nuhkoca
 */
public class VideoViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable;
    private PlayerUseCase playerUseCase;

    private MutableLiveData<PlayerResponse> mPlayerResponse = new MutableLiveData<>();

    /**
     * A default constructor that inject required dependencies
     *
     * @param compositeDisposable represents an instance of {@link CompositeDisposable}
     * @param playerUseCase       represents an instance of {@link PlayerUseCase}
     */
    @Inject
    VideoViewModel(@NonNull CompositeDisposable compositeDisposable,
                   @NonNull PlayerUseCase playerUseCase) {
        this.compositeDisposable = compositeDisposable;
        this.playerUseCase = playerUseCase;
    }

    /**
     * Returns playable content
     *
     * @param videoId indicates the video id
     */
    void getPlayableContent(@NonNull String videoId) {
        Disposable url = playerUseCase.execute(new DisposableSingleObserver<PlayerResponse>() {
            @Override
            public void onSuccess(PlayerResponse playerResponse) {
                mPlayerResponse.setValue(playerResponse);
            }

            @Override
            public void onError(Throwable e) {

            }
        }, videoId);

        compositeDisposable.add(url);
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
