package com.nuhkoca.myapplication.ui.video;

import com.nuhkoca.myapplication.data.remote.player.PlayerResponse;
import com.nuhkoca.myapplication.repository.PlayerRepository;
import com.nuhkoca.myapplication.repository.UseCase;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import io.reactivex.Single;

/**
 * A [UseCase] pattern for playable content
 *
 * @author nuhkoca
 */
public class PlayerUseCase extends UseCase<PlayerResponse, String> {

    /**
     * A default constructor that inject required dependencies
     *
     * @param playerRepository represents an instance of {@link PlayerRepository}
     */
    @Inject
    PlayerUseCase(PlayerRepository playerRepository) {
        super(playerRepository);
    }

    /**
     * Build use case with the power of RxJava
     *
     * @param params represents params to be passed
     * @return a {@link Single} response
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public Single<PlayerResponse> buildUseCaseObservable(@Nullable String params) {
        return getPlayerRepository().getPlayableContent(params);
    }
}
