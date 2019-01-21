package com.nuhkoca.myapplication;

import com.nuhkoca.myapplication.di.component.AppComponent;
import com.nuhkoca.myapplication.di.component.BindingComponent;
import com.nuhkoca.myapplication.di.component.DaggerAppComponent;
import com.nuhkoca.myapplication.di.component.DaggerBindingComponent;

import androidx.databinding.DataBindingUtil;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * An {@link DaggerApplication} that handles Dagger setup.
 *
 * @author nuhkoca
 */
public class App extends DaggerApplication {

    /**
     * Returns the injector
     *
     * @return the injector
     */
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);

        BindingComponent bindingComponent = DaggerBindingComponent.builder().appComponent(appComponent).build();
        DataBindingUtil.setDefaultComponent(bindingComponent);

        return appComponent;
    }
}
