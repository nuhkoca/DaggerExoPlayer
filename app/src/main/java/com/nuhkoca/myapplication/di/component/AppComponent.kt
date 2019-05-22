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
     * Factory of [AppComponent]
     */
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}
