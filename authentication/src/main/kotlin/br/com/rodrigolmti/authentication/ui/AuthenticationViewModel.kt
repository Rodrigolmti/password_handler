package br.com.rodrigolmti.authentication.ui

import androidx.lifecycle.MutableLiveData
import br.com.rodrigolmti.core_android.SingleLiveEvent
import br.com.rodrigolmti.core_android.base.BaseViewModel
import javax.inject.Inject

internal class AuthenticationViewState @Inject constructor() {

    val action: SingleLiveEvent<Action> = SingleLiveEvent()
    val state: MutableLiveData<State> = MutableLiveData()

    enum class State { IDLE, LOADING }

    sealed class Action {
        object Created : Action()
        object Authenticated : Action()
        object Unauthenticated : Action()
    }
}

internal sealed class AuthenticationAction {
    data class CreateBackupPin(val pin: String) : AuthenticationAction()
    data class CheckBackupPin(val pin: String) : AuthenticationAction()
}

internal class AuthenticationViewModel @Inject constructor(
    override val viewState: AuthenticationViewState,
) :
    BaseViewModel<AuthenticationViewState, AuthenticationAction>() {

    override fun dispatchViewAction(viewAction: AuthenticationAction) {
       when(viewAction) {
           is AuthenticationAction.CheckBackupPin -> {}
           is AuthenticationAction.CreateBackupPin -> {}
       }
    }
}