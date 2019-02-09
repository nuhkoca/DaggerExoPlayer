package com.nuhkoca.myapplication.di.module

import com.nuhkoca.myapplication.binding.ImageBindingAdapter
import com.nuhkoca.myapplication.binding.SpannableTextBindingAdapter
import com.nuhkoca.myapplication.di.scope.DataBinding

import androidx.databinding.BindingAdapter
import dagger.Module
import dagger.Provides

/**
 * A module that handles [BindingAdapter] classes as generic.
 *
 * @author nuhkoca
 */
@Module
class BindingModule {

    /**
     * Returns an instance of [SpannableTextBindingAdapter]
     *
     * @return an instance of [SpannableTextBindingAdapter]
     */
    @DataBinding
    @Provides
    internal fun bindsSpannableTextBindingAdapter(): SpannableTextBindingAdapter {
        return SpannableTextBindingAdapter()
    }

    /**
     * Returns an instance of [ImageBindingAdapter]
     *
     * @return an instance of [ImageBindingAdapter]
     */
    @DataBinding
    @Provides
    internal fun bindsImageBindingAdapter(): ImageBindingAdapter {
        return ImageBindingAdapter()
    }
}
