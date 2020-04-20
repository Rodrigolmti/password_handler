package br.com.rodrigolmti.dashboard.ui.password_generator

import androidx.lifecycle.viewModelScope
import br.com.rodrigolmti.core_android.base.BaseViewModel
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.dashboard.domain.model.PasswordGeneratorModel
import br.com.rodrigolmti.dashboard.domain.use_cases.GeneratePasswordUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class PasswordGeneratorViewModel @Inject constructor(
    override val viewState: PasswordGeneratorState,
    private val generatePasswordUseCase: GeneratePasswordUseCase
) : BaseViewModel<PasswordGeneratorState, PasswordGeneratorAction>() {

    init {
        viewState.state.value = PasswordGeneratorState.State.IDLE
    }

    override fun dispatchViewAction(viewAction: PasswordGeneratorAction) {
        when (viewAction) {
            is PasswordGeneratorAction.GeneratePassword -> {
                generatePassword(viewAction)
            }
        }.exhaustive
    }

    private fun generatePassword(action: PasswordGeneratorAction.GeneratePassword) =
        viewModelScope.launch {
            if (validateMinimumParams(action) && validateMinimumLength(
                    action.passwordLength,
                    action.passwordNumber
                )
            ) {
                viewState.state.value = PasswordGeneratorState.State.LOADING

                val passwordGenerator = PasswordGeneratorModel(
                    passwordLength = action.passwordLength,
                    passwordNumber = action.passwordNumber,
                    isUpperCase = action.isUpperCase,
                    isLowerCase = action.isLowerCase,
                    isNumbers = action.isNumbers,
                    isSpecialChars = action.isSpecialChars
                )

                viewState.state.value = PasswordGeneratorState.State.IDLE
                generatePasswordUseCase(passwordGenerator).handleResult(
                    onSuccess = {
                        viewState.action.value = PasswordGeneratorState.Action.ShowPasswordList(it)
                    },
                    onError = {
                        viewState.action.value = PasswordGeneratorState.Action.ShowError
                    }
                )
            }
        }

    private fun validateMinimumParams(action: PasswordGeneratorAction.GeneratePassword): Boolean {
        if (!action.isUpperCase && !action.isLowerCase && !action.isNumbers && !action.isSpecialChars) {
            viewState.action.value = PasswordGeneratorState.Action.ShowNoParamSelectedError
            return false
        }
        return true
    }

    private fun validateMinimumLength(length: Int, passwordCount: Int): Boolean {
        if (length <= 0 || passwordCount <= 0) {
            viewState.action.value = PasswordGeneratorState.Action.ShowNumberTooSmallError
            return false
        }
        return true
    }
}