package com.nuhkoca.myapplication.di.component

import androidx.databinding.DataBindingComponent
import com.nuhkoca.myapplication.binding.ImageBindingAdapter
import com.nuhkoca.myapplication.binding.SpannableTextBindingAdapter
import com.nuhkoca.myapplication.di.module.BindingModule
import com.nuhkoca.myapplication.di.scope.DataBinding
import dagger.Component

@DataBinding
@Component(dependencies = [AppComponent::class], modules = [BindingModule::class])
interface BindingComponent : DataBindingComponent {

    override fun getImageBindingAdapter(): ImageBindingAdapter?

    override fun getSpannableTextBindingAdapter(): SpannableTextBindingAdapter?
}
