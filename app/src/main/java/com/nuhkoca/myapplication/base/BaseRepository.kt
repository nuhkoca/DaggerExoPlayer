package com.nuhkoca.myapplication.base

import android.content.Context

import com.nuhkoca.myapplication.R
import com.nuhkoca.myapplication.api.IExoAPI

import org.jetbrains.annotations.Contract
import io.reactivex.Single
import retrofit2.Response

/**
 * A base repository class that provides an instance of [IExoAPI]
 *
 * @author nuhkoca
 */
open class BaseRepository(protected val iExoAPIService: IExoAPI,
                          private val context: Context) {

    /**
     * Merges a response with in-case error.
     *
     * @param <T> represents any generic item
     * @return a result as [Single]
    </T> */
    @Contract("null->fail")
    protected fun <T> interceptError(response: Response<T>): Single<Response<T>> {
        val requestCode = response.code()

        if (requestCode == 401) { /* do nothing this error is handled in Auth interceptor */
            return Single.error(Throwable(context.getString(R.string.auth_error)))
        } else if (requestCode in 400..501) {
            try {
                return Single.error(Throwable(response.message()))
            } catch (ignore: Exception) {
                return Single.error(Throwable(context.getString(R.string.network_call_initial_error)))
            }

        }

        return Single.just(response)
    }
}
