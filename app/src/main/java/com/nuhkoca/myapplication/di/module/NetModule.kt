package com.nuhkoca.myapplication.di.module

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nuhkoca.myapplication.BuildConfig
import com.nuhkoca.myapplication.api.AuthInterceptor
import com.nuhkoca.myapplication.api.IExoAPI
import com.nuhkoca.myapplication.helper.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * A [Module] that handles network injections
 *
 * @author nuhkoca
 */
@Module
class NetModule {

    /**
     * Returns an instance of Gson
     *
     * @return an instance of [Gson]
     */
    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .serializeNulls()
            .setLenient()
            .create()
    }

    /**
     * Returns an instance of [HttpLoggingInterceptor]
     *
     * @return an instance of [HttpLoggingInterceptor]
     */
    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    /**
     * Returns an instance of [OkHttpClient]
     *
     * @param httpLoggingInterceptor represents an instance of [HttpLoggingInterceptor]
     * @param authInterceptor        represents an instance of [AuthInterceptor]
     * @return an instance of [OkHttpClient]
     */
    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(Constants.DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClient.readTimeout(Constants.DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClient.writeTimeout(Constants.DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)

        httpClient.addInterceptor(authInterceptor)
        httpClient.interceptors().add(httpLoggingInterceptor)

        return httpClient.build()
    }

    /**
     * Returns an instance of AuthInterceptor
     *
     * @return an instance of [AuthInterceptor]
     */
    @Provides
    @Singleton
    internal fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    /**
     * Returns an instance of [Retrofit]
     *
     * @param okHttpClient represents an instance of [OkHttpClient]
     * @param gson         represents an instance of [Gson]
     * @return an instance of [Retrofit]
     */
    @Provides
    @Singleton
    @Named("video")
    internal fun provideRetrofitVideo(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    /**
     * Returns an instance of [Retrofit]
     *
     * @param gson represents an instance of [Gson]
     * @return an instance of [Retrofit]
     */
    @Provides
    @Singleton
    @Named("player")
    internal fun provideRetrofitPlayer(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_PLAYER_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    /**
     * Returns an instance of IExoAPI
     *
     * @param retrofit represents an instance of [IExoAPI]
     * @return an instance of [IExoAPI]
     */
    @Provides
    @Singleton
    @Named("videoService")
    internal fun provideIPetteServiceVideo(@Named("video") retrofit: Retrofit): IExoAPI {
        return retrofit.create(IExoAPI::class.java)
    }

    /**
     * Returns an instance of IExoAPI
     *
     * @param retrofit represents an instance of [IExoAPI]
     * @return an instance of [IExoAPI]
     */
    @Provides
    @Singleton
    @Named("playerService")
    internal fun provideIPetteServicePlayer(@Named("player") retrofit: Retrofit): IExoAPI {
        return retrofit.create(IExoAPI::class.java)
    }
}
