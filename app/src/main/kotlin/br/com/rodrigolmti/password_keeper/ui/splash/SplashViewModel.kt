package br.com.rodrigolmti.password_keeper.ui.splash

import androidx.lifecycle.viewModelScope
import br.com.rodrigolmti.core_android.base.BaseViewModel
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.security.domain.use_case.GenerateKeysUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SplashViewModel @Inject constructor(
    override val viewState: SplashViewState,
    private val generateKeysUseCase: GenerateKeysUseCase
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
            viewState.action.value = SplashViewState.Action.KeyGenerationSucceeded
        }, onError = {
            viewState.action.value = SplashViewState.Action.KeyGenerationError
        })
    }
}