package com.nuhkoca.myapplication.di.module;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * A {@link Module} that injects general stuffs and includes other modules
 *
 * @author nuhkoca
 */
@Module(includes = {ActivityBuilder.class, ContextModule.class, ExoModule.class, ViewModelModule.class, NetModule.class})
public class AppModule {

    /**
     * Returns an instance of {@link CompositeDisposable}
     *
     * @return an instance of {@link CompositeDisposable}
     */
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
