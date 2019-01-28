package com.nuhkoca.myapplication.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.nuhkoca.myapplication.helper.Constants;

import androidx.annotation.NonNull;
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
     * Returns an instance of {@link SharedPreferences}
     *
     * @param context represents an instance of {@link Context}
     * @return an instance {@link SharedPreferences}
     */
    @Provides
    SharedPreferences provideSharedPreference(@NonNull Context context) {
        return context.getSharedPreferences(Constants.EXO_PREF_NAME, Context.MODE_PRIVATE);
    }

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
