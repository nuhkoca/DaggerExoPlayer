package com.nuhkoca.myapplication.di.module;

import android.content.Context;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;
import com.nuhkoca.myapplication.R;
import com.nuhkoca.myapplication.helper.Constants;
import com.nuhkoca.myapplication.helper.DefaultCacheDataSourceFactory;

import java.io.File;
import java.net.CookieManager;
import java.net.CookiePolicy;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

/**
 * A {@link Module} that injects ExoPlayer
 *
 * @author nuhkoca
 */
@Module
public class ExoModule {

    /**
     * Provides the user agent for the device
     *
     * @param context represents an instance of {@link Context}
     * @return an instance of user agent
     */
    @Singleton
    @Provides
    public String provideUserAgent(@NonNull Context context) {
        return Util.getUserAgent(context, Constants.EXO_PLAYER_USER_AGENT);
    }

    /**
     * Provides an instance of {@link DefaultBandwidthMeter}
     *
     * @return an instance of {@link DefaultBandwidthMeter}
     */
    @Singleton
    @Provides
    public DefaultBandwidthMeter provideDefaultBandwidthMeter() {
        return new DefaultBandwidthMeter();
    }

    /**
     * Provides an instance of {@link HttpDataSource.Factory}
     *
     * @param userAgent      represents the user agent
     * @param bandwidthMeter represents an instance of {@link DefaultBandwidthMeter}
     * @return an instance of {@link HttpDataSource.Factory}
     */
    @Singleton
    @Provides
    public HttpDataSource.Factory provideHttpDataSourceFactory(@NonNull String userAgent, @NonNull DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter);
    }

    /**
     * Provides an instance of {@link TrackSelection.Factory}
     *
     * @return an instance of {@link TrackSelection.Factory}
     */
    @Singleton
    @Provides
    public TrackSelection.Factory provideTrackSelectionFactory() {
        return new AdaptiveTrackSelection.Factory();
    }

    /**
     * Provides an instance of {@link DefaultTrackSelector}
     *
     * @param factory represents an instance of {@link TrackSelection.Factory}
     * @return an instance of {@link DefaultTrackSelector}
     */
    @Singleton
    @Provides
    public DefaultTrackSelector provideDefaultTrackSelector(@NonNull TrackSelection.Factory factory) {
        return new DefaultTrackSelector(factory);
    }

    /**
     * Provides an instance of {@link DefaultRenderersFactory}
     *
     * @param context represents an instance of {@link Context}
     * @return an instance of {@link DefaultRenderersFactory}
     */
    @Provides
    public DefaultRenderersFactory provideDefaultRenderersFactory(@NonNull Context context) {
        return new DefaultRenderersFactory(context);
    }

    /**
     * Provides an instance of {@link LoadControl}
     *
     * @return an instance of {@link LoadControl}
     */
    @Provides
    public LoadControl provideLoadControl() {
        return new DefaultLoadControl();
    }

    /**
     * Provides an instance of {@link SimpleExoPlayer}
     *
     * @param context          represents an instance of {@link Context}
     * @param renderersFactory represents an instance of {@link DefaultRenderersFactory}
     * @param trackSelector    represents an instance of {@link DefaultTrackSelector}
     * @param loadControl      represents an instance of {@link DefaultLoadControl}
     * @return an instance of {@link SimpleExoPlayer}
     */
    @Provides
    public SimpleExoPlayer provideExoPlayer(@NonNull Context context,
                                            @NonNull DefaultRenderersFactory renderersFactory,
                                            @NonNull DefaultTrackSelector trackSelector,
                                            @NonNull LoadControl loadControl) {
        return ExoPlayerFactory.newSimpleInstance(context, renderersFactory, trackSelector, loadControl);
    }

    /**
     * Provides an instance of {@link DefaultDataSourceFactory}
     *
     * @param context        represents an instance of {@link Context}
     * @param bandwidthMeter represents an instance of {@link DefaultBandwidthMeter}
     * @param factory        represents an instance of {@link HttpDataSource.Factory}
     * @return an instance of {@link DefaultDataSourceFactory}
     */
    @Singleton
    @Provides
    public DefaultDataSourceFactory provideDefaultDataSourceFactory(@NonNull Context context,
                                                                    @NonNull DefaultBandwidthMeter bandwidthMeter,
                                                                    @NonNull HttpDataSource.Factory factory) {
        return new DefaultDataSourceFactory(context, bandwidthMeter, factory);
    }

    /**
     * Provides an instance of {@link LeastRecentlyUsedCacheEvictor}
     *
     * @return an instance of {@link LeastRecentlyUsedCacheEvictor}
     */
    @Singleton
    @Provides
    public LeastRecentlyUsedCacheEvictor provideLeastRecentlyUsedCacheEvictor() {
        return new LeastRecentlyUsedCacheEvictor(Constants.EXO_PLAYER_VIDEO_CACHE_DURATION);
    }

    /**
     * Provides an instance of {@link DataSource.Factory}
     *
     * @param cacheDataSource represents an instance of {@link CacheDataSource}
     * @return an instance of {@link DataSource.Factory}
     */
    @Singleton
    @Provides
    public DataSource.Factory provideDataSourceFactory(@NonNull CacheDataSource cacheDataSource) {
        return new DefaultCacheDataSourceFactory(cacheDataSource);
    }

    /**
     * Returns an instance of {@link ExtractorMediaSource.Factory}
     *
     * @param factory represents an instance of {@link DataSource.Factory}
     * @return an instance of {@link ExtractorMediaSource.Factory}
     */
    @Singleton
    @Provides
    public ExtractorMediaSource.Factory provideExtractorMediaSourceFactory(@NonNull DataSource.Factory factory) {
        return new ExtractorMediaSource.Factory(factory);
    }

    /**
     * Provides an instance of {@link SimpleCache}
     *
     * @param context represents an instance of {@link Context}
     * @param evictor represents an instance of {@link LeastRecentlyUsedCacheEvictor}
     * @return an instance of {@link SimpleCache}
     */
    @Singleton
    @Provides
    public SimpleCache provideSimpleCache(@NonNull Context context, @NonNull LeastRecentlyUsedCacheEvictor evictor) {
        return new SimpleCache(new File(context.getCacheDir(), context.getString(R.string.cache_type)), evictor);
    }

    /**
     * Provides an instance of {@link CacheDataSource}
     *
     * @param simpleCache   represents an instance of {@link SimpleCache}
     * @param factory       represents an instance of {@link DefaultDataSourceFactory}
     * @param cacheDataSink represents an instance of {@link CacheDataSink}
     * @return an instance of {@link CacheDataSource}
     */
    @Singleton
    @Provides
    public CacheDataSource provideCacheDataSource(@NonNull SimpleCache simpleCache,
                                                  @NonNull DefaultDataSourceFactory factory,
                                                  @NonNull CacheDataSink cacheDataSink) {
        return new CacheDataSource(simpleCache, factory.createDataSource(), new FileDataSource(), cacheDataSink,
                CacheDataSource.FLAG_BLOCK_ON_CACHE | CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR, null);
    }

    /**
     * Provides an instance of {@link CacheDataSink}
     *
     * @param simpleCache represents an instance of {@link SimpleCache}
     * @return an instance of {@link CacheDataSink}
     */
    @Singleton
    @Provides
    public CacheDataSink provideCacheDataSink(@NonNull SimpleCache simpleCache) {
        return new CacheDataSink(simpleCache, Constants.EXO_PLAYER_VIDEO_CACHE_DURATION);
    }

    /**
     * Returns an instance of {@link CookieManager}
     *
     * @return an instance of {@link CookieManager}
     */
    @Singleton
    @Provides
    public CookieManager provideCookieManager() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        return cookieManager;
    }
}
