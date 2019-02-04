package com.nuhkoca.myapplication.repository;

import androidx.annotation.Nullable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * The [UseCase] helper class that runs repository methods
 * @param <M> represents model
 * @param <P> represents params
 *
 * @author nuhkoca
 */
public abstract class UseCase<M, P> {

    private PlayerRepository playerRepository;

    /**
     * A default constructor that inject required dependencies
     *
     * @param playerRepository represents an instance of {@link PlayerRepository}
     */
    public UseCase(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public abstract Single<M> buildUseCaseObservable(@Nullable P params);

    /**
     * Executes the target call
     *
     * @param observer indicates an observer for the process
     * @param params   represents params to be passed
     * @return {@link Disposable} result
     */
    public Disposable execute(DisposableSingleObserver<M> observer, @Nullable P params) {
        if (params == null) {
            return null;
        }

        final Single<M> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return observable.subscribeWith(observer);
    }

    /**
     * Returns an instance of {@link PlayerRepository}
     *
     * @return an instance of {@link PlayerRepository}
     */
    protected PlayerRepository getPlayerRepository() {
        return playerRepository;
    }
}
