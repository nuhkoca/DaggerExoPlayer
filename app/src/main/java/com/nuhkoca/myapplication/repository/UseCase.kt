package com.nuhkoca.myapplication.repository

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * The [UseCase] helper class that runs repository methods
 * @param <M> represents model
 * @param <P> represents params
 *
 * @author nuhkoca
</P></M> */
abstract class UseCase<M, P>(protected val playerRepository: PlayerRepository) {

    abstract fun buildUseCaseObservable(params: P?): Single<M>

    /**
     * Executes the target call
     *
     * @param observer indicates an observer for the process
     * @param params   represents params to be passed
     * @return [Disposable] result
     */
    fun execute(observer: DisposableSingleObserver<M>, params: P?): Disposable? {
        if (params == null) {
            return null
        }

        val observable = this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        return observable.subscribeWith(observer)
    }
}
