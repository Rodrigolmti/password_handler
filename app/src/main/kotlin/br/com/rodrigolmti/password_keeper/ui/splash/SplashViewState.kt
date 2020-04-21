package br.com.rodrigolmti.password_keeper.ui.splash

import androidx.lifecycle.MutableLiveData
import br.com.rodrigolmti.core_android.SingleLiveEvent
import javax.inject.Inject

internal class SplashViewState @Inject constructor() {

    val action: SingleLiveEvent<Action> = SingleLiveEvent()
    val state: MutableLiveData<State> = MutableLiveData()

    enum class State { IDLE, LOADING }

    sealed class Action {

    }
}