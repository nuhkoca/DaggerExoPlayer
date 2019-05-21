package com.nuhkoca.myapplication.ui.video

import com.nuhkoca.myapplication.data.remote.player.PlayerResponse
import com.nuhkoca.myapplication.repository.PlayerRepository
import com.nuhkoca.myapplication.repository.UseCase

import javax.inject.Inject
import io.reactivex.Single

/**
 * A [UseCase] pattern for playable content
 *
 * @author nuhkoca
 */
class PlayerUseCase
/**
 * A default constructor that inject required dependencies
 *
 * @param playerRepository represents an instance of [PlayerRepository]
 */
@Inject
internal constructor(playerRepository: PlayerRepository) : UseCase<PlayerResponse, String>(playerRepository) {

    /**
     * Build use case with the power of RxJava
     *
     * @param params represents params to be passed
     * @return a [Single] response
     */
    override fun buildUseCaseObservable(params: String?): Single<PlayerResponse> {
        return playerRepository.getPlayableContent(params!!)
    }
}
