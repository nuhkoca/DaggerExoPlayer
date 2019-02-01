package com.nuhkoca.myapplication.di.module;

import com.nuhkoca.myapplication.di.scope.PerActivity;
import com.nuhkoca.myapplication.ui.main.MainActivity;
import com.nuhkoca.myapplication.ui.video.VideoActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * A {@link Module} that injects activities
 *
 * @author nuhkoca
 */
@Module
public abstract class ActivityBuilder {

    /**
     * Injects {@link MainActivity}
     *
     * @return an instance of {@link MainActivity}
     */
    @PerActivity
    @ContributesAndroidInjector
    abstract MainActivity contributesMainActivityInjector();

    /**
     * Injects {@link VideoActivity}
     *
     * @return an instance of {@link VideoActivity}
     */
    @PerActivity
    @ContributesAndroidInjector
    abstract VideoActivity contributesVideoActivityInjector();
}
