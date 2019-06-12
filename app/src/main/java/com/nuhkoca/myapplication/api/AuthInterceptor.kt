package com.nuhkoca.myapplication.api

import com.nuhkoca.myapplication.BuildConfig
import com.nuhkoca.myapplication.helper.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A class that includes sensitive data to each network call
 *
 * @author nuhkoca
 */
@Singleton
class AuthInterceptor @Inject constructor() : Interceptor {

    /**
     * Adds specific headers to requests
     *
     * @param chain represents [Chain]
     * @return response with headers and others
     * @throws IOException throws
     */
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url()
        val url = originalUrl.newBuilder()
            .addQueryParameter(Constants.TRENDING_KEY, Constants.TRENDING_VALUE)
            .addQueryParameter(Constants.LIKES_KEY, Constants.LIKES_VALUE)
            .build()
        val requestBuilder = original.newBuilder().url(url)
        val newRequest = requestBuilder
            .addHeader("Authorization", "Bearer " + BuildConfig.ACCESS_TOKEN)
            .addHeader("Connection", "close")
            .method(chain.request().method(), chain.request().body())
            .build()

        return chain.proceed(newRequest)
    }
}
