package br.com.rodrigolmti.core_android.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    fun <VM : ViewModel> getViewModel(modelClass: Class<VM>): VM =
        ViewModelProvider(this, viewModelFactory).get(modelClass)
}