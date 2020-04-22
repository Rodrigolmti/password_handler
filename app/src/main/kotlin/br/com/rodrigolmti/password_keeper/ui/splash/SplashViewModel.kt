package br.com.rodrigolmti.password_keeper.ui.splash

import androidx.lifecycle.viewModelScope
import br.com.rodrigolmti.core_android.base.BaseViewModel
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.security.domain.use_case.GenerateKeysUseCase
import br.com.rodrigolmti.security.domain.use_case.GetUserBiometricUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SplashViewModel @Inject constructor(
    override val viewState: SplashViewState,
    private val generateKeysUseCase: GenerateKeysUseCase,
    private val getUserBiometricUseCase: GetUserBiometricUseCase
) : BaseViewModel<SplashViewState, SplashAction>() {

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override fun dispatchViewAction(viewAction: SplashAction) {
        when (viewAction) {
            is SplashAction.Init -> {
                generateKeys()
            }
        }.exhaustive
    }

    private fun generateKeys() = viewModelScope.launch {
        generateKeysUseCase().handleResult(onSuccess = {
            if (getUserBiometricUseCase()) {
                viewState.action.value = SplashViewState.Action.ValidateBiometric
            } else {
                viewState.action.value = SplashViewState.Action.NavigateToDashboard
            }
        }, onError = {
            viewState.action.value = SplashViewState.Action.NavigateToDashboard
            //TODO: Handle the error, log events and bla bla
        })
    }
}