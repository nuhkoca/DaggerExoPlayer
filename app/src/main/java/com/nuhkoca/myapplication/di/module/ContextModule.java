package com.nuhkoca.myapplication.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * A {@link Module} that is resposible for injecting only {@link Context}
 */
@Module
public abstract class ContextModule {

    /**
     * Binds and returns an instance of {@link Context}
     *
     * @param application represents an instance of {@link Application}
     * @return an instance of {@link Context}
     */
    @Binds
    abstract Context bindsContext(Application application);
}
