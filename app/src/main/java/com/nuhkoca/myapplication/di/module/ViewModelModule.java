package com.nuhkoca.myapplication.di.module;

import com.nuhkoca.myapplication.di.qualifier.ViewModelKey;
import com.nuhkoca.myapplication.ui.main.MainViewModel;
import com.nuhkoca.myapplication.ui.video.VideoViewModel;
import com.nuhkoca.myapplication.vm.ExoViewModelFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * A {@link Module} that injects ViewModels
 *
 * @author nuhkoca
 */
@Module
public abstract class ViewModelModule {

    /**
     * Returns an instance of {@link MainViewModel}
     *
     * @param mainViewModel represents an instance of {@link MainViewModel}
     * @returnan instance of {@link MainViewModel}
     */
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindsNewsDetailViewModel(MainViewModel mainViewModel);

    /**
     * Returns an instance of {@link VideoViewModel}
     *
     * @param videoViewModel represents an instance of {@link VideoViewModel}
     * @returnan instance of {@link VideoViewModel}
     */
    @Binds
    @IntoMap
    @ViewModelKey(VideoViewModel.class)
    abstract ViewModel bindsVideoViewModel(VideoViewModel videoViewModel);

    /**
     * Returns an instance of {@link ExoViewModelFactory}
     *
     * @param exoViewModelFactory an instance of {@link ExoViewModelFactory}
     * @return an instance of {@link ExoViewModelFactory}
     */
    @Binds
    abstract ViewModelProvider.Factory bindsSertificarViewModelFactory(@NonNull ExoViewModelFactory exoViewModelFactory);
}
