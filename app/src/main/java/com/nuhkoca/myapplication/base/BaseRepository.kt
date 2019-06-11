package com.nuhkoca.myapplication.base

import android.content.Context
import com.nuhkoca.myapplication.R
import com.nuhkoca.myapplication.api.IExoAPI
import io.reactivex.Single
import org.jetbrains.annotations.Contract
import org.json.JSONObject
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_NOT_IMPLEMENTED
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

/**
 * A base repository class that provides an instance of [IExoAPI]
 *
 * @author nuhkoca
 */
abstract class BaseRepository(
    protected val iExoAPIService: IExoAPI,
    private val context: Context
) {

    /**
     * Merges a response with in-case error.
     *
     * @param <T> represents any generic item
     * @return a result as [Single]
    </T> */
    @Contract("null->fail")
    protected fun <T> interceptError(response: Response<T>): Single<Response<T>> {
        val requestCode = response.code()

        response.errorBody()?.let { body ->
            val jObjError = JSONObject(body.string())
            if (requestCode == HTTP_UNAUTHORIZED) { /* do nothing this error is handled in Auth interceptor */
                return Single.error(Throwable(jObjError.get("error").toString()))
            } else if (requestCode in HTTP_BAD_REQUEST..HTTP_NOT_IMPLEMENTED) {
                return try {
                    Single.error(Throwable(jObjError.get("error").toString()))
                } catch (ignore: Exception) {
                    Single.error(Throwable(context.getString(R.string.network_call_initial_error)))
                }
            }
        }

        return Single.just(response)
    }
}
