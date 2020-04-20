package br.com.rodrigolmti.dashboard.ui.password

import androidx.lifecycle.viewModelScope
import br.com.rodrigolmti.core_android.base.BaseViewModel
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.domain.use_cases.*
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class PasswordViewModel @Inject constructor(
    override val viewState: PasswordViewState,
    private val savePasswordUseCase: SavePasswordUseCase,
    private val updateSavedPassword: UpdateSavedPassword,
    private val passwordStrengthUseCase: PasswordStrengthUseCase,
    private val generateRandomPasswordUseCase: GenerateRandomPasswordUseCase,
    private val deleteSavedPasswordUseCase: DeleteSavedPasswordUseCase
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
            is PasswordAction.DeleteSavedPassword -> {
                deleteSavedPassword(viewAction.model)
            }
            is PasswordAction.ShufflePassword -> {
                shufflePassword()
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
                    val model = SavedPasswordModel(
                        id = action.id,
                        label = action.label,
                        login = action.login,
                        password = action.password,
                        strength = action.strength
                    )

                    updateSavedPassword(model).handleResult(
                        onSuccess = {
                            viewState.action.value =
                                PasswordViewState.Action.ShowUpdatePasswordSuccess
                        },
                        onError = {
                            viewState.action.value =
                                PasswordViewState.Action.ShowUpdatePasswordError
                        },
                        onFinish = {
                            viewState.state.value = PasswordViewState.State.IDLE
                        }
                    )
                } ?: run {
                    val model = SavedPasswordModel(
                        label = action.label,
                        login = action.login,
                        password = action.password,
                        strength = action.strength
                    )

                    savePasswordUseCase(model).handleResult(
                        onSuccess = {
                            viewState.action.value =
                                PasswordViewState.Action.ShowSavePasswordSuccess
                        },
                        onError = {
                            viewState.action.value =
                                PasswordViewState.Action.ShowSavePasswordError
                        },
                        onFinish = {
                            viewState.state.value = PasswordViewState.State.IDLE
                        }
                    )
                }
            }
        }
    }

    private fun deleteSavedPassword(model: SavedPasswordModel) = viewModelScope.launch {
        viewState.state.value = PasswordViewState.State.LOADING

        deleteSavedPasswordUseCase(model).handleResult(
            onSuccess = {
                viewState.action.value =
                    PasswordViewState.Action.ShowDeletePasswordSuccess
            },
            onError = {
                viewState.action.value =
                    PasswordViewState.Action.ShowDeletePasswordError
            },
            onFinish = {
                viewState.state.value = PasswordViewState.State.IDLE
            }
        )
    }

    private fun shufflePassword() = viewModelScope.launch {
        viewState.state.value = PasswordViewState.State.LOADING

        generateRandomPasswordUseCase().handleResult(
            onSuccess = {
                viewState.action.value =
                    PasswordViewState.Action.ShowGeneratedPassword(it)
            },
            onError = {
                viewState.action.value =
                    PasswordViewState.Action.ShowGeneratePasswordError
            },
            onFinish =  {
                viewState.state.value = PasswordViewState.State.IDLE
            }
        )
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