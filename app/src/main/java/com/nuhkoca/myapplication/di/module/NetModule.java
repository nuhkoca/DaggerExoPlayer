package com.nuhkoca.myapplication.di.module;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nuhkoca.myapplication.BuildConfig;
import com.nuhkoca.myapplication.api.AuthInterceptor;
import com.nuhkoca.myapplication.api.IExoAPI;
import com.nuhkoca.myapplication.helper.Constants;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A {@link Module} that handles network injections
 *
 * @author nuhkoca
 */
@Module
public class NetModule {

    /**
     * Returns an instance of Gson
     *
     * @return an instance of {@link Gson}
     */
    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .serializeNulls()
                .setLenient()
                .create();
    }

    /**
     * Returns an instance of {@link HttpLoggingInterceptor}
     *
     * @return an instance of {@link HttpLoggingInterceptor}
     */
    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    /**
     * Returns an instance of {@link OkHttpClient}
     *
     * @param httpLoggingInterceptor represents an instance of {@link HttpLoggingInterceptor}
     * @param authInterceptor        represents an instance of {@link AuthInterceptor}
     * @return an instance of {@link OkHttpClient}
     */
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(@NonNull HttpLoggingInterceptor httpLoggingInterceptor, @NonNull AuthInterceptor authInterceptor) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        httpClient.addInterceptor(authInterceptor);
        httpClient.interceptors().add(httpLoggingInterceptor);

        return httpClient.build();
    }

    /**
     * Returns an instance of AuthInterceptor
     *
     * @return an instance of {@link AuthInterceptor}
     */
    @Provides
    @Singleton
    AuthInterceptor provideAuthInterceptor() {
        return new AuthInterceptor();
    }

    /**
     * Returns an instance of {@link Retrofit}
     *
     * @param okHttpClient represents an instance of {@link OkHttpClient}
     * @param gson         represents an instance of {@link Gson}
     * @return an instance of {@link Retrofit}
     */
    @Provides
    @Singleton
    @Named("video")
    Retrofit provideRetrofitVideo(@NonNull OkHttpClient okHttpClient, @NonNull Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    /**
     * Returns an instance of {@link Retrofit}
     *
     * @param gson represents an instance of {@link Gson}
     * @return an instance of {@link Retrofit}
     */
    @Provides
    @Singleton
    @Named("player")
    Retrofit provideRetrofitPlayer(@NonNull Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_PLAYER_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    /**
     * Returns an instance of IExoAPI
     *
     * @param retrofit represents an instance of {@link IExoAPI}
     * @return an instance of {@link IExoAPI}
     */
    @Provides
    @Singleton
    @Named("videoService")
    IExoAPI provideIPetteServiceVideo(@NonNull @Named("video") Retrofit retrofit) {
        return retrofit.create(IExoAPI.class);
    }

    /**
     * Returns an instance of IExoAPI
     *
     * @param retrofit represents an instance of {@link IExoAPI}
     * @return an instance of {@link IExoAPI}
     */
    @Provides
    @Singleton
    @Named("playerService")
    IExoAPI provideIPetteServicePlayer(@NonNull @Named("player") Retrofit retrofit) {
        return retrofit.create(IExoAPI.class);
    }
}
