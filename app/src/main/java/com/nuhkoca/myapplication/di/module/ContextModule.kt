package com.nuhkoca.myapplication.di.module

import android.app.Application
import android.content.Context

import dagger.Binds
import dagger.Module

/**
 * A [Module] that is resposible for injecting only [Context]
 */
@Module
abstract class ContextModule {

    /**
     * Binds and returns an instance of [Context]
     *
     * @param application represents an instance of [Application]
     * @return an instance of [Context]
     */
    @Binds
    internal abstract fun bindsContext(application: Application): Context
}
