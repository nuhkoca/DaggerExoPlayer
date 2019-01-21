package com.nuhkoca.myapplication.di.module;

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
    @ContributesAndroidInjector
    abstract MainActivity contributesMainActivityInjector();

    /**
     * Injects {@link VideoActivity}
     *
     * @return an instance of {@link VideoActivity}
     */
    @ContributesAndroidInjector
    abstract VideoActivity contributesVideoActivityInjector();
}
