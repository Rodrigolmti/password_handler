package br.com.rodrigolmti.dashboard.ui.password

import androidx.lifecycle.viewModelScope
import br.com.rodrigolmti.core_android.base.BaseViewModel
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.domain.use_cases.PasswordStrengthUseCase
import br.com.rodrigolmti.dashboard.domain.use_cases.SavePasswordUseCase
import br.com.rodrigolmti.dashboard.ui.passwords.PasswordsAction
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class PasswordViewModel @Inject constructor(
    override val viewState: PasswordViewState,
    private val savePasswordUseCase: SavePasswordUseCase,
    private val passwordStrengthUseCase: PasswordStrengthUseCase
) : BaseViewModel<PasswordViewState, PasswordAction>() {

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override fun dispatchViewAction(viewAction: PasswordAction) {
        when (viewAction) {
            is PasswordAction.CalculatePasswordStrength -> {
                calculatePasswordStrength(viewAction.password)
            }
            is PasswordAction.SavePassword -> {
                savePassword(action = viewAction)
            }
        }.exhaustive
    }

    private fun calculatePasswordStrength(password: String) = viewModelScope.launch {
        viewState.action.value =
            PasswordViewState.Action.ShowPasswordStrength(passwordStrengthUseCase(password))
    }

    private fun savePassword(action: PasswordAction.SavePassword) {
        viewModelScope.launch {
            if (validatePasswordLabel(action.label) && validatePassword(action.password)) {
                viewState.state.value = PasswordViewState.State.LOADING

                action.id?.let {

                } ?: run {

                    val model = SavedPasswordModel(
                        label = action.label,
                        login = action.login,
                        password = action.password,
                        strength = action.strength
                    )

                    savePasswordUseCase(model).handleResult(
                        onSuccess = {
                            print("")
                        },
                        onError = {
                            print("")
                        }
                    )
                }
            }
        }
    }

    private fun validatePasswordLabel(label: String?): Boolean {
        if (label.isNullOrEmpty()) {
            viewState.action.value =
                PasswordViewState.Action.ShowInvalidPasswordLabel
            return false
        }
        return true
    }

    private fun validatePassword(password: String?): Boolean {
        if (password.isNullOrEmpty()) {
            viewState.action.value =
                PasswordViewState.Action.ShowInvalidPassword
            return false
        }
        return true
    }
}