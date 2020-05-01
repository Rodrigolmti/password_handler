package br.com.rodrigolmti.authentication.ui.pin

import br.com.rodrigolmti.core_android.SingleLiveEvent
import javax.inject.Inject

class PinViewState @Inject constructor() {

    val action: SingleLiveEvent<Action> = SingleLiveEvent()

    sealed class Action {
    }
}