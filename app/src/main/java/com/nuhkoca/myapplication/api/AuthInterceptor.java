package com.nuhkoca.myapplication.api;

import com.nuhkoca.myapplication.BuildConfig;
import com.nuhkoca.myapplication.helper.Constants;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A class that includes sensitive data to each network call
 *
 * @author nuhkoca
 */
@Singleton
public class AuthInterceptor implements Interceptor {

    /**
     * A default constructor that gets dependencies
     */
    @Inject
    public AuthInterceptor() {
    }

    /**
     * Adds specific headers to requests
     *
     * @param chain represents {@link Chain}
     * @return response with headers and others
     * @throws IOException throws
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalUrl = original.url();
        HttpUrl url = originalUrl.newBuilder()
                .addQueryParameter(Constants.TRENDING_KEY, Constants.TRENDING_VALUE)
                .addQueryParameter(Constants.LIKES_KEY, Constants.LIKES_VALUE)
                .build();
        Request.Builder requestBuilder = original.newBuilder().url(url);
        Request newRequest = requestBuilder
                .addHeader("Authorization", "Bearer " + BuildConfig.ACCESS_TOKEN)
                .method(chain.request().method(), chain.request().body())
                .build();
        return chain.proceed(newRequest);
    }
}
