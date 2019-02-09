package com.nuhkoca.myapplication.ui.video

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nuhkoca.myapplication.data.remote.player.PlayerResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

/**
 * A [androidx.lifecycle.ViewModel] for media playing
 *
 * @author nuhkoca
 */
class VideoViewModel
/**
 * A default constructor that inject required dependencies
 *
 * @param compositeDisposable represents an instance of [CompositeDisposable]
 * @param playerUseCase       represents an instance of [PlayerUseCase]
 */
@Inject
internal constructor(private val compositeDisposable: CompositeDisposable,
                     private val playerUseCase: PlayerUseCase) : ViewModel() {

    /**
     * Returns the playable content
     *
     * @return the playable content
     */
    internal val content = MutableLiveData<PlayerResponse>()

    /**
     * Returns playable content
     *
     * @param videoId indicates the video id
     */
    internal fun getPlayableContent(videoId: String) {
        val url = playerUseCase.execute(object : DisposableSingleObserver<PlayerResponse>() {
            override fun onSuccess(playerResponse: PlayerResponse) {
                content.value = playerResponse
            }

            override fun onError(e: Throwable) {

            }
        }, videoId)

        compositeDisposable.add(url!!)
    }

    /**
     * Clears references
     */
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
