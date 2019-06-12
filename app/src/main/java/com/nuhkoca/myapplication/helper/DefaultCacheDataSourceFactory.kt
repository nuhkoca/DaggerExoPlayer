package com.nuhkoca.myapplication.helper

import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource

/**
 * A [DataSource.Factory] that implements cache mechanism
 *
 * @param cacheDataSource represents an instance of [CacheDataSource]
 *
 * @author nuhkoca
 */
class DefaultCacheDataSourceFactory constructor(private val cacheDataSource: CacheDataSource) : DataSource.Factory {

    /**
     * Creates the data source
     *
     * @return an instance of [DataSource]
     */
    override fun createDataSource(): DataSource {
        return cacheDataSource
    }
}
