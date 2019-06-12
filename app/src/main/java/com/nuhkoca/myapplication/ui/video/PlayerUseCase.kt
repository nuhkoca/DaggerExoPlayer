package com.nuhkoca.myapplication.ui.video

import com.nuhkoca.myapplication.data.remote.player.PlayerResponse
import com.nuhkoca.myapplication.repository.PlayerRepository
import com.nuhkoca.myapplication.repository.UseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 * A [UseCase] pattern for playable content
 *
 * @param playerRepository represents an instance of [PlayerRepository]
 *
 * @author nuhkoca
 */
class PlayerUseCase @Inject constructor(playerRepository: PlayerRepository) :
    UseCase<PlayerResponse, String>(playerRepository) {

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
