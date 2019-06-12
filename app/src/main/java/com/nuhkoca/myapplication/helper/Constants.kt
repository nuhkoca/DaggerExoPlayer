package com.nuhkoca.myapplication.helper

import com.nuhkoca.myapplication.BuildConfig

import java.util.Locale

/**
 * A helper class that holds appliation constants
 *
 * @author nuhkoca
 */
object Constants {
    const val EXO_PLAYER_USER_AGENT = BuildConfig.APPLICATION_ID
    const val EXO_PLAYER_VIDEO_CACHE_DURATION = 10 * 1024 * 1024
    const val DEFAULT_TIMEOUT = 60
    const val EXECUTOR_THREAD_POOL_OFFSET = 5
    const val INITIAL_LOAD_SIZE_HINT = 10
    const val OFFSET_SIZE = 10
    const val TRENDING_KEY = "filter"
    const val TRENDING_VALUE = "trending"
    const val LIKES_KEY = "sort"
    const val LIKES_VALUE = "likes"
    const val VIDEO_KEY = "video"
    const val CURRENT_POSITION_KEY = "exo_current_position"
    const val EXO_PREF_NAME = "exo_shared_pref"
    val locale = Locale("tr", "TR", "tr")
}
