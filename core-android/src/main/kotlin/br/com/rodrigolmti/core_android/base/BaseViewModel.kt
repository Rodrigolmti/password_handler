package br.com.rodrigolmti.core_android.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<State, Action> : ViewModel() {

    abstract val viewState: State
    abstract fun dispatchViewAction(viewAction: Action)
}