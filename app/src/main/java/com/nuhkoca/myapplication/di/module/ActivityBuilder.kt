package com.nuhkoca.myapplication.di.module

import com.nuhkoca.myapplication.di.scope.PerActivity
import com.nuhkoca.myapplication.ui.main.MainActivity
import com.nuhkoca.myapplication.ui.video.VideoActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * A [Module] that injects activities
 *
 * @author nuhkoca
 */
@Module
abstract class ActivityBuilder {

    /**
     * Injects [MainActivity]
     *
     * @return an instance of [MainActivity]
     */
    @PerActivity
    @ContributesAndroidInjector
    internal abstract fun contributesMainActivityInjector(): MainActivity

    /**
     * Injects [VideoActivity]
     *
     * @return an instance of [VideoActivity]
     */
    @PerActivity
    @ContributesAndroidInjector
    internal abstract fun contributesVideoActivityInjector(): VideoActivity
}
