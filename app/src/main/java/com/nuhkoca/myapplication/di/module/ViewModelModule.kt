package com.nuhkoca.myapplication.di.module

import com.nuhkoca.myapplication.di.qualifier.ViewModelKey
import com.nuhkoca.myapplication.ui.main.MainViewModel
import com.nuhkoca.myapplication.ui.video.VideoViewModel
import com.nuhkoca.myapplication.vm.ExoViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * A [Module] that injects ViewModels
 *
 * @author nuhkoca
 */
@Module
abstract class ViewModelModule {

    /**
     * Returns an instance of [MainViewModel]
     *
     * @param mainViewModel represents an instance of [MainViewModel]
     * @return an instance of [MainViewModel]
     */
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindsNewsDetailViewModel(mainViewModel: MainViewModel): ViewModel

    /**
     * Returns an instance of [VideoViewModel]
     *
     * @param videoViewModel represents an instance of [VideoViewModel]
     * @return an instance of [VideoViewModel]
     */
    @Binds
    @IntoMap
    @ViewModelKey(VideoViewModel::class)
    internal abstract fun bindsVideoViewModel(videoViewModel: VideoViewModel): ViewModel

    /**
     * Returns an instance of [ExoViewModelFactory]
     *
     * @param exoViewModelFactory an instance of [ExoViewModelFactory]
     * @return an instance of [ExoViewModelFactory]
     */
    @Binds
    internal abstract fun bindsSertificarViewModelFactory(exoViewModelFactory: ExoViewModelFactory): ViewModelProvider.Factory
}
