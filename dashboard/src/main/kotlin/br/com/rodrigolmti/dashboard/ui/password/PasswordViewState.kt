package br.com.rodrigolmti.dashboard.ui.password

import androidx.lifecycle.MutableLiveData
import br.com.rodrigolmti.core_android.SingleLiveEvent
import javax.inject.Inject

internal class PasswordViewState @Inject constructor() {

    val action: SingleLiveEvent<Action> = SingleLiveEvent()
    val state: MutableLiveData<State> = MutableLiveData()

    enum class State { IDLE, LOADING }

    sealed class Action {
        data class ShowPasswordStrength(val strength: Int) : Action()
        object ShowInvalidPasswordLabel : Action()
        object ShowUpdatePasswordSuccess : Action()
        object ShowSavePasswordSuccess : Action()
        object ShowDeletePasswordSuccess : Action()
        object ShowUpdatePasswordError : Action()
        object ShowSavePasswordError : Action()
        object ShowDeletePasswordError : Action()
        object ShowInvalidPassword : Action()
    }
}