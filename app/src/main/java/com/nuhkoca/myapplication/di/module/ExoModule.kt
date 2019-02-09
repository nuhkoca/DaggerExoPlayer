package com.nuhkoca.myapplication.di.module

import android.content.Context

import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.LoadControl
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.FileDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSink
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util
import com.nuhkoca.myapplication.R
import com.nuhkoca.myapplication.helper.Constants
import com.nuhkoca.myapplication.helper.DefaultCacheDataSourceFactory

import java.io.File
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy

import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * A [Module] that injects ExoPlayer
 *
 * @author nuhkoca
 */
@Module
class ExoModule {

    /**
     * Provides the user agent for the device
     *
     * @param context represents an instance of [Context]
     * @return an instance of user agent
     */
    @Singleton
    @Provides
    fun provideUserAgent(context: Context): String {
        return Util.getUserAgent(context, Constants.EXO_PLAYER_USER_AGENT)
    }

    /**
     * Provides an instance of [DefaultBandwidthMeter]
     *
     * @return an instance of [DefaultBandwidthMeter]
     */
    @Singleton
    @Provides
    fun provideDefaultBandwidthMeter(): DefaultBandwidthMeter {
        return DefaultBandwidthMeter()
    }

    /**
     * Provides an instance of [HttpDataSource.Factory]
     *
     * @param userAgent      represents the user agent
     * @param bandwidthMeter represents an instance of [DefaultBandwidthMeter]
     * @return an instance of [HttpDataSource.Factory]
     */
    @Singleton
    @Provides
    fun provideHttpDataSourceFactory(userAgent: String, bandwidthMeter: DefaultBandwidthMeter): HttpDataSource.Factory {
        return DefaultHttpDataSourceFactory(userAgent, bandwidthMeter)
    }

    /**
     * Provides an instance of [TrackSelection.Factory]
     *
     * @return an instance of [TrackSelection.Factory]
     */
    @Singleton
    @Provides
    fun provideTrackSelectionFactory(): TrackSelection.Factory {
        return AdaptiveTrackSelection.Factory()
    }

    /**
     * Provides an instance of [DefaultTrackSelector]
     *
     * @param factory represents an instance of [TrackSelection.Factory]
     * @return an instance of [DefaultTrackSelector]
     */
    @Reusable
    @Provides
    fun provideDefaultTrackSelector(factory: TrackSelection.Factory): DefaultTrackSelector {
        return DefaultTrackSelector(factory)
    }

    /**
     * Provides an instance of [DefaultRenderersFactory]
     *
     * @param context represents an instance of [Context]
     * @return an instance of [DefaultRenderersFactory]
     */
    @Reusable
    @Provides
    fun provideDefaultRenderersFactory(context: Context): DefaultRenderersFactory {
        return DefaultRenderersFactory(context)
    }

    /**
     * Provides an instance of [LoadControl]
     *
     * @return an instance of [LoadControl]
     */
    @Reusable
    @Provides
    fun provideLoadControl(): LoadControl {
        return DefaultLoadControl()
    }

    /**
     * Provides an instance of [AudioAttributes]
     * This is used to interrupt audio in case of getting
     * call or playing a music.
     *
     * @return an instance of [AudioAttributes]
     */
    @Singleton
    @Provides
    fun provideAudioAttributes(): AudioAttributes {
        return AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.CONTENT_TYPE_SPEECH)
                .build()
    }

    /**
     * Provides an instance of [SimpleExoPlayer]
     *
     * @param context          represents an instance of [Context]
     * @param renderersFactory represents an instance of [DefaultRenderersFactory]
     * @param trackSelector    represents an instance of [DefaultTrackSelector]
     * @param loadControl      represents an instance of [DefaultLoadControl]
     * @param audioAttributes  represents an instance of [AudioAttributes]
     * @return an instance of [SimpleExoPlayer]
     */
    @Provides
    fun provideExoPlayer(context: Context,
                         renderersFactory: DefaultRenderersFactory,
                         trackSelector: DefaultTrackSelector,
                         loadControl: LoadControl,
                         audioAttributes: AudioAttributes): SimpleExoPlayer {
        val exoPlayer = ExoPlayerFactory.newSimpleInstance(context, renderersFactory, trackSelector, loadControl)
        exoPlayer.setAudioAttributes(audioAttributes, true)
        exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
        return exoPlayer
    }

    /**
     * Provides an instance of [DefaultDataSourceFactory]
     *
     * @param context        represents an instance of [Context]
     * @param bandwidthMeter represents an instance of [DefaultBandwidthMeter]
     * @param factory        represents an instance of [HttpDataSource.Factory]
     * @return an instance of [DefaultDataSourceFactory]
     */
    @Reusable
    @Provides
    fun provideDefaultDataSourceFactory(context: Context,
                                        bandwidthMeter: DefaultBandwidthMeter,
                                        factory: HttpDataSource.Factory): DefaultDataSourceFactory {
        return DefaultDataSourceFactory(context, bandwidthMeter, factory)
    }

    /**
     * Provides an instance of [LeastRecentlyUsedCacheEvictor]
     *
     * @return an instance of [LeastRecentlyUsedCacheEvictor]
     */
    @Reusable
    @Provides
    fun provideLeastRecentlyUsedCacheEvictor(): LeastRecentlyUsedCacheEvictor {
        return LeastRecentlyUsedCacheEvictor(Constants.EXO_PLAYER_VIDEO_CACHE_DURATION.toLong())
    }

    /**
     * Provides an instance of [DataSource.Factory]
     *
     * @param cacheDataSource represents an instance of [CacheDataSource]
     * @return an instance of [DataSource.Factory]
     */
    @Reusable
    @Provides
    fun provideDataSourceFactory(cacheDataSource: CacheDataSource): DataSource.Factory {
        return DefaultCacheDataSourceFactory(cacheDataSource)
    }

    /**
     * Returns an instance of [ExtractorMediaSource.Factory]
     *
     * @param factory represents an instance of [DataSource.Factory]
     * @return an instance of [ExtractorMediaSource.Factory]
     */
    @Reusable
    @Provides
    fun provideExtractorMediaSourceFactory(factory: DataSource.Factory): ExtractorMediaSource.Factory {
        return ExtractorMediaSource.Factory(factory)
    }

    /**
     * Provides an instance of [SimpleCache]
     *
     * @param context represents an instance of [Context]
     * @param evictor represents an instance of [LeastRecentlyUsedCacheEvictor]
     * @return an instance of [SimpleCache]
     */
    @Singleton
    @Provides
    fun provideSimpleCache(context: Context, evictor: LeastRecentlyUsedCacheEvictor): SimpleCache {
        return SimpleCache(File(context.cacheDir, context.getString(R.string.cache_type)), evictor)
    }

    /**
     * Provides an instance of [CacheDataSource]
     *
     * @param simpleCache   represents an instance of [SimpleCache]
     * @param factory       represents an instance of [DefaultDataSourceFactory]
     * @param cacheDataSink represents an instance of [CacheDataSink]
     * @return an instance of [CacheDataSource]
     */
    @Reusable
    @Provides
    fun provideCacheDataSource(simpleCache: SimpleCache,
                               factory: DefaultDataSourceFactory,
                               cacheDataSink: CacheDataSink): CacheDataSource {
        return CacheDataSource(simpleCache, factory.createDataSource(), FileDataSource(), cacheDataSink,
                CacheDataSource.FLAG_BLOCK_ON_CACHE or CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR, null)
    }

    /**
     * Provides an instance of [CacheDataSink]
     *
     * @param simpleCache represents an instance of [SimpleCache]
     * @return an instance of [CacheDataSink]
     */
    @Reusable
    @Provides
    fun provideCacheDataSink(simpleCache: SimpleCache): CacheDataSink {
        return CacheDataSink(simpleCache, Constants.EXO_PLAYER_VIDEO_CACHE_DURATION.toLong())
    }

    /**
     * Returns an instance of [CookieManager]
     *
     * @return an instance of [CookieManager]
     */
    @Singleton
    @Provides
    fun provideCookieManager(): CookieManager {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER)
        return cookieManager
    }

    /**
     * Returns an instance of [CookieHandler]
     *
     * @param cookieManager represents an instance of [CookieManager]
     * @return an instance of [CookieHandler]
     */
    @Singleton
    @Provides
    fun provideCookieHandler(cookieManager: CookieManager): CookieHandler {
        if (CookieHandler.getDefault() !== cookieManager) {
            CookieHandler.setDefault(cookieManager)
        }

        return CookieHandler.getDefault()
    }
}
