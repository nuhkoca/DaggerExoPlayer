package com.nuhkoca.myapplication

import androidx.databinding.DataBindingUtil
import com.nuhkoca.myapplication.di.component.DaggerAppComponent
import com.nuhkoca.myapplication.di.component.DaggerBindingComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * An [DaggerApplication] that handles Dagger setup.
 *
 * @author nuhkoca
 */
class App : DaggerApplication() {

    /**
     * Returns the injector
     *
     * @return the injector
     */
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)

        val bindingComponent = DaggerBindingComponent.builder().appComponent(appComponent).build()
        DataBindingUtil.setDefaultComponent(bindingComponent)

        return appComponent
    }
}
