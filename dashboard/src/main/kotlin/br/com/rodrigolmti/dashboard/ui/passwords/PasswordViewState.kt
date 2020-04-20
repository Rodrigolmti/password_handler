package br.com.rodrigolmti.dashboard.ui.passwords

import androidx.lifecycle.MutableLiveData
import br.com.rodrigolmti.core_android.SingleLiveEvent
import br.com.rodrigolmti.dashboard.domain.model.PasswordModel
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.ui.password_generator.PasswordGeneratorViewState
import javax.inject.Inject

internal class PasswordsViewState @Inject constructor() {

    val action: SingleLiveEvent<Action> = SingleLiveEvent()
    val state: MutableLiveData<State> = MutableLiveData()

    enum class State { IDLE, LOADING, ERROR, EMPTY_LIST }

    sealed class Action {
        data class ShowSavedPasswordList(val passwords: List<SavedPasswordModel>) : Action()
        object GetSavedPasswordsError : Action()
    }
}