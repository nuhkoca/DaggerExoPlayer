package com.nuhkoca.myapplication.di.module;

import com.nuhkoca.myapplication.binding.ImageBindingAdapter;
import com.nuhkoca.myapplication.binding.SpannableTextBindingAdapter;
import com.nuhkoca.myapplication.di.qualifier.DataBinding;

import androidx.databinding.BindingAdapter;
import dagger.Module;
import dagger.Provides;

/**
 * A module that handles {@link BindingAdapter} classes as generic.
 *
 * @author nuhkoca
 */
@Module
public class BindingModule {

    /**
     * Returns an instance of {@link SpannableTextBindingAdapter}
     *
     * @return an instance of {@link SpannableTextBindingAdapter}
     */
    @DataBinding
    @Provides
    SpannableTextBindingAdapter bindsSpannableTextBindingAdapter() {
        return new SpannableTextBindingAdapter();
    }

    /**
     * Returns an instance of {@link ImageBindingAdapter}
     *
     * @return an instance of {@link ImageBindingAdapter}
     */
    @DataBinding
    @Provides
    ImageBindingAdapter bindsImageBindingAdapter() {
        return new ImageBindingAdapter();
    }
}
