package br.com.rodrigolmti.dashboard.ui.password_generator

import androidx.lifecycle.viewModelScope
import br.com.rodrigolmti.core_android.base.BaseViewModel
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.dashboard.domain.model.PasswordGenerationModel
import br.com.rodrigolmti.dashboard.domain.model.PasswordModel
import br.com.rodrigolmti.dashboard.domain.use_cases.GeneratePreDeterminedPasswordUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class PasswordGeneratorModel @Inject constructor() {
    var passwords: List<PasswordModel> = emptyList()
}

internal class PasswordGeneratorViewModel @Inject constructor(
    override val viewState: PasswordGeneratorViewState,
    private val model: PasswordGeneratorModel,
    private val generatePreDeterminedPasswordUseCase: GeneratePreDeterminedPasswordUseCase
) : BaseViewModel<PasswordGeneratorViewState, PasswordGeneratorAction>() {

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override fun dispatchViewAction(viewAction: PasswordGeneratorAction) {
        when (viewAction) {
            is PasswordGeneratorAction.InitView -> {
                model.passwords.takeIf { it.isNotEmpty() }?.let {
                    viewState.action.value =
                        PasswordGeneratorViewState.Action.ShowPasswordList(model.passwords)
                }
            }
            is PasswordGeneratorAction.GeneratePassword -> {
                generatePassword(viewAction)
            }
            is PasswordGeneratorAction.ClearModel -> {
                model.passwords = emptyList()
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
                viewState.state.value = PasswordGeneratorViewState.State.LOADING

                val passwordGenerator = PasswordGenerationModel(
                    passwordLength = action.passwordLength,
                    passwordNumber = action.passwordNumber,
                    isUpperCase = action.isUpperCase,
                    isLowerCase = action.isLowerCase,
                    isNumbers = action.isNumbers,
                    isSpecialChars = action.isSpecialChars
                )

                generatePreDeterminedPasswordUseCase(passwordGenerator).handleResult(
                    onSuccess = {
                        viewState.state.value = PasswordGeneratorViewState.State.IDLE
                        viewState.action.value =
                            PasswordGeneratorViewState.Action.ShowPasswordList(it)
                        model.passwords = it
                    },
                    onError = {
                        viewState.state.value = PasswordGeneratorViewState.State.IDLE
                        viewState.action.value = PasswordGeneratorViewState.Action.ShowError
                    }
                )
            }
        }

    private fun validateMinimumParams(action: PasswordGeneratorAction.GeneratePassword): Boolean {
        if (!action.isUpperCase && !action.isLowerCase && !action.isNumbers && !action.isSpecialChars) {
            viewState.action.value = PasswordGeneratorViewState.Action.ShowNoParamSelectedError
            return false
        }
        return true
    }

    private fun validateMinimumLength(length: Int, passwordCount: Int): Boolean {
        if (length <= 0 || passwordCount <= 0) {
            viewState.action.value = PasswordGeneratorViewState.Action.ShowNumberTooSmallError
            return false
        }
        return true
    }
}