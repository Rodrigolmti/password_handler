package br.com.rodrigolmti.dashboard.ui.passwords

import androidx.lifecycle.viewModelScope
import br.com.rodrigolmti.core_android.base.BaseViewModel
import br.com.rodrigolmti.dashboard.domain.use_cases.GetAllSavedPasswordsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class PasswordsViewModel @Inject constructor(
    private val getAllSavedPasswordsUseCase: GetAllSavedPasswordsUseCase,
    override val viewState: PasswordsViewState
) : BaseViewModel<PasswordsViewState, PasswordsAction>() {

    override fun dispatchViewAction(viewAction: PasswordsAction) {
        when(viewAction) {
            is PasswordsAction.Init -> {
                initAction()
            }
        }
    }

    private fun initAction() = viewModelScope.launch {
        viewState.state.value = PasswordsViewState.State.LOADING
        getAllSavedPasswordsUseCase().handleResult(
            onSuccess =  {
                viewState.state.value = PasswordsViewState.State.IDLE
                viewState.action.value = PasswordsViewState.Action.ShowSavedPasswordList(it)
            },
            onError =  {
                viewState.state.value = PasswordsViewState.State.ERROR
                viewState.action.value = PasswordsViewState.Action.GetSavedPasswordsError
            }
        )
    }
}