package br.com.rodrigolmti.dashboard.ui.settings

import androidx.lifecycle.viewModelScope
import br.com.rodrigolmti.core_android.base.BaseViewModel
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.security.domain.use_case.GetUserBiometricUseCase
import br.com.rodrigolmti.security.domain.use_case.UpdateUserBiometricUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SettingsViewModel @Inject constructor(
    override val viewState: SettingsViewState,
    private val getUserBiometricUseCase: GetUserBiometricUseCase,
    private val updateUserBiometricUseCase: UpdateUserBiometricUseCase
) : BaseViewModel<SettingsViewState, SettingsAction>() {

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override fun dispatchViewAction(viewAction: SettingsAction) {
        when (viewAction) {
            is SettingsAction.Init -> {
                getBiometric()
            }
            is SettingsAction.BiometricChanged -> {
                updateBiometric(viewAction.checked)
            }
        }.exhaustive
    }

    private fun getBiometric() = viewModelScope.launch {
        viewState.action.value =
            SettingsViewState.Action.UpdateBiometricSwitch(getUserBiometricUseCase())
    }

    private fun updateBiometric(checked: Boolean) = viewModelScope.launch {
        updateUserBiometricUseCase(checked)
    }
}