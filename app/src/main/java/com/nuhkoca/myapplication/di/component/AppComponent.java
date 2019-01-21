package com.nuhkoca.myapplication.di.component;

import android.app.Application;

import com.nuhkoca.myapplication.App;
import com.nuhkoca.myapplication.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * A main {@link Component} that conducts everything for injection
 *
 * @author nuhkoca
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class})
public interface AppComponent extends AndroidInjector<App> {

    /**
     * Injects the main app
     *
     * @param instance represents an instance of of {@link App}
     */
    @Override
    void inject(App instance);

    /**
     * Builder of {@link AppComponent}
     */
    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
