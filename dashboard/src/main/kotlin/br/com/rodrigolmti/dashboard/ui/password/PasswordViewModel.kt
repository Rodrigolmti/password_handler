package br.com.rodrigolmti.dashboard.ui.password

import androidx.lifecycle.viewModelScope
import br.com.rodrigolmti.core_android.base.BaseViewModel
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.dashboard.domain.use_cases.PasswordStrengthUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class PasswordViewModel @Inject constructor(
    override val viewState: PasswordViewState,
    private val passwordStrengthUseCase: PasswordStrengthUseCase
) : BaseViewModel<PasswordViewState, PasswordAction>() {

    override fun dispatchViewAction(viewAction: PasswordAction) {
        when (viewAction) {
            is PasswordAction.CalculatePasswordStrength -> {
                calculatePasswordStrength(viewAction.password)
            }
        }.exhaustive
    }

    private fun calculatePasswordStrength(password: String) = viewModelScope.launch {
        viewState.action.value =
            PasswordViewState.Action.ShowPasswordStrength(passwordStrengthUseCase(password))
    }
}