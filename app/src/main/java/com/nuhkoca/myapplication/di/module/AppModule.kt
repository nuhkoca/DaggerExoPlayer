package com.nuhkoca.myapplication.di.module

import android.content.Context
import android.content.SharedPreferences

import com.nuhkoca.myapplication.helper.Constants
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * A [Module] that injects general stuffs and includes other modules
 *
 * @author nuhkoca
 */
@Module(includes = [ActivityBuilder::class, ContextModule::class, ExoModule::class, ViewModelModule::class, NetModule::class])
class AppModule {

    /**
     * Returns an instance of [SharedPreferences]
     *
     * @param context represents an instance of [Context]
     * @return an instance [SharedPreferences]
     */
    @Provides
    internal fun provideSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.EXO_PREF_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Returns an instance of [CompositeDisposable]
     *
     * @return an instance of [CompositeDisposable]
     */
    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}
