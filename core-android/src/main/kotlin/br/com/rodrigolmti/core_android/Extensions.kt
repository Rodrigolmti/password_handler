package br.com.rodrigolmti.core_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner

typealias CreateViewModel = (handle: SavedStateHandle) -> ViewModel

inline fun <reified VM : ViewModel> Fragment.viewModelByFactory(
    defaultArgs: Bundle? = null,
    noinline create: CreateViewModel
): Lazy<VM> {
    return viewModels {
        createViewModelFactoryFactory(this, defaultArgs, create)
    }
}

inline fun <reified VM : ViewModel> Fragment.activityViewModelByFactory(
    defaultArgs: Bundle? = null,
    noinline create: CreateViewModel
): Lazy<VM> {
    return activityViewModels {
        createViewModelFactoryFactory(this, defaultArgs, create)
    }
}

fun createViewModelFactoryFactory(
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle?,
    create: CreateViewModel
): ViewModelProvider.Factory {
    return object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            @Suppress("UNCHECKED_CAST")
            return create(handle) as? T
                ?: throw IllegalArgumentException("Unknown viewmodel class!")
        }
    }
}