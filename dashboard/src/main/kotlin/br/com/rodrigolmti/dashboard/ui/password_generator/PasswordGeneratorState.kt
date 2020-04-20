package br.com.rodrigolmti.dashboard.ui.password_generator

import androidx.lifecycle.MutableLiveData
import br.com.rodrigolmti.core_android.SingleLiveEvent
import br.com.rodrigolmti.dashboard.domain.model.PasswordModel
import javax.inject.Inject

class PasswordGeneratorState @Inject constructor() {

    val action: SingleLiveEvent<Action> = SingleLiveEvent()
    val state: MutableLiveData<State> = MutableLiveData()

    enum class State { IDLE, LOADING }

    sealed class Action {
        data class ShowPasswordList(val passwords: List<PasswordModel>) : Action()
        object ShowNoParamSelectedError: Action()
        object ShowNumberTooSmallError: Action()
        object ShowError : Action()
    }
}