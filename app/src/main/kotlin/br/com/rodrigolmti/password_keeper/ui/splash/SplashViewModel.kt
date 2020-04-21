package br.com.rodrigolmti.password_keeper.ui.splash

import br.com.rodrigolmti.core_android.base.BaseViewModel
import br.com.rodrigolmti.core_android.extensions.exhaustive
import javax.inject.Inject

internal class SplashViewModel @Inject constructor(
    override val viewState: SplashViewState
) : BaseViewModel<SplashViewState, SplashAction>() {

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override fun dispatchViewAction(viewAction: SplashAction) {

    }
}