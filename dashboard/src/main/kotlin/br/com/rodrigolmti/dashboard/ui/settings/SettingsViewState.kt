package br.com.rodrigolmti.dashboard.ui.settings

import br.com.rodrigolmti.core_android.SingleLiveEvent
import javax.inject.Inject

internal class SettingsViewState @Inject constructor() {

    val action: SingleLiveEvent<Action> = SingleLiveEvent()

    sealed class Action {
        data class UpdateBiometricSwitch(val checked: Boolean) : Action()
    }
}