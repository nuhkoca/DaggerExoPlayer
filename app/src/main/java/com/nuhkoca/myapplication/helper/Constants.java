package com.nuhkoca.myapplication.helper;

import com.nuhkoca.myapplication.BuildConfig;

import java.util.Locale;

/**
 * A helper class that holds appliation constants
 *
 * @author nuhkoca
 */
public final class Constants {

    public static final String EXO_PLAYER_USER_AGENT = BuildConfig.APPLICATION_ID;
    public static final int EXO_PLAYER_VIDEO_CACHE_DURATION = 10 * 1024 * 1024;
    public static final int DEFAULT_TIMEOUT = 60;
    public static final int EXECUTOR_THREAD_POOL_OFFSET = 5;
    public static final int INITIAL_LOAD_SIZE_HINT = 10;
    public static final int OFFSET_SIZE = 10;
    public static final String TRENDING_KEY = "filter";
    public static final String TRENDING_VALUE = "trending";
    public static final String LIKES_KEY = "sort";
    public static final String LIKES_VALUE = "likes";
    public static final String VIDEO_KEY = "video";
    public static final Locale locale = new Locale("tr", "TR", "tr");

    public Constants() {
        throw new AssertionError();
    }
}
