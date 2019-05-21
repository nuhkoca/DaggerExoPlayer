package com.nuhkoca.myapplication.di.component

import android.app.Application
import com.nuhkoca.myapplication.App
import com.nuhkoca.myapplication.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * A main [Component] that conducts everything for injection
 *
 * @author nuhkoca
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class])
interface AppComponent : AndroidInjector<App> {

    /**
     * Injects the main app
     *
     * @param instance represents an instance of of [App]
     */
    override fun inject(instance: App)

    /**
     * Builder of [AppComponent]
     */
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}
