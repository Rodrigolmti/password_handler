package br.com.rodrigolmti.core_android.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    fun <VM : ViewModel> getViewModel(modelClass: Class<VM>): VM =
        ViewModelProvider(this, viewModelFactory).get(modelClass)
}