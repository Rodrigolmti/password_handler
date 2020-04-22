package br.com.rodrigolmti.dashboard.ui.settings

internal sealed class SettingsAction {
    data class BiometricChanged(val checked: Boolean) : SettingsAction()
    object Init : SettingsAction()
}