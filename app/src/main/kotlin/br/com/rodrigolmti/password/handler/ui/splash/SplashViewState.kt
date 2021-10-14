package br.com.rodrigolmti.password.handler.ui.splash

import br.com.rodrigolmti.core_android.SingleLiveEvent
import javax.inject.Inject

internal class SplashViewState @Inject constructor() {

    val action: SingleLiveEvent<Action> = SingleLiveEvent()

    sealed class Action {
        object ValidateBiometric : Action()
        object NavigateToDashboard : Action()
    }
}