package br.com.rodrigolmti.authentication.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.rodrigolmti.authentication.domain.use_cases.CheckPinUseCase
import br.com.rodrigolmti.authentication.domain.use_cases.SavePinUseCase
import br.com.rodrigolmti.core_android.SingleLiveEvent
import br.com.rodrigolmti.core_android.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class AuthenticationViewState @Inject constructor() {

    val action: SingleLiveEvent<Action> = SingleLiveEvent()
    val state: MutableLiveData<State> = MutableLiveData()

    enum class State { IDLE, LOADING }

    sealed class Action {
        object Created : Action()
        object Authenticated : Action()
        object Unauthenticated : Action()
        object SavePinError : Action()
    }
}

internal sealed class AuthenticationAction {
    data class SavePin(val pin: String) : AuthenticationAction()
    data class CheckPin(val pin: String) : AuthenticationAction()
}

internal class AuthenticationViewModel @Inject constructor(
    override val viewState: AuthenticationViewState,
    private val checkPinUseCase: CheckPinUseCase,
    private val savePinUseCase: SavePinUseCase,
) :
    BaseViewModel<AuthenticationViewState, AuthenticationAction>() {

    override fun dispatchViewAction(viewAction: AuthenticationAction) {
        when (viewAction) {
            is AuthenticationAction.CheckPin -> checkPin(viewAction.pin)
            is AuthenticationAction.SavePin -> savePin(viewAction.pin)
        }
    }

    private fun checkPin(pin: String) = viewModelScope.launch {
        viewState.state.value = AuthenticationViewState.State.LOADING
        checkPinUseCase(pin).handleResult(
            onSuccess = {
                viewState.state.value = AuthenticationViewState.State.IDLE
                viewState.action.value = AuthenticationViewState.Action.Authenticated
            },
            onError = {
                viewState.action.value = AuthenticationViewState.Action.Unauthenticated
            }
        )
    }

    private fun savePin(pin: String) = viewModelScope.launch {
        viewState.state.value = AuthenticationViewState.State.LOADING
        savePinUseCase(pin).handleResult(
            onSuccess = {
                viewState.state.value = AuthenticationViewState.State.IDLE
                viewState.action.value = AuthenticationViewState.Action.Created
            },
            onError = {
                viewState.action.value = AuthenticationViewState.Action.SavePinError
            }
        )
    }
}