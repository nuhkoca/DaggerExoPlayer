package com.nuhkoca.myapplication.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * A [ViewModelProvider.Factory] that is injected onto each [ViewModel] by [ExoViewModelFactory].
 */
@Suppress("UNCHECKED_CAST")
@Singleton
class ExoViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}
