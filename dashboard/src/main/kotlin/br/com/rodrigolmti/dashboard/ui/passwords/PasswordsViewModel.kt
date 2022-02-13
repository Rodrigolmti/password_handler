package br.com.rodrigolmti.dashboard.ui.passwords

import androidx.lifecycle.viewModelScope
import br.com.rodrigolmti.core_android.base.BaseViewModel
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.domain.use_cases.GetAllSavedPasswordsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class PasswordsModel @Inject constructor() {
    var passwords: List<SavedPasswordModel> = emptyList()
}

internal class PasswordsViewModel @Inject constructor(
    override val viewState: PasswordsViewState,
    private val model: PasswordsModel,
    private val getAllSavedPasswordsUseCase: GetAllSavedPasswordsUseCase
) : BaseViewModel<PasswordsViewState, PasswordsAction>() {

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override fun dispatchViewAction(viewAction: PasswordsAction) {
        when (viewAction) {
            is PasswordsAction.Init -> {
                initAction()
            }
            is PasswordsAction.FilterPasswords -> {
                filterPassword(viewAction.query)
            }
        }
    }

    private fun initAction() = viewModelScope.launch {
        viewState.state.value = PasswordsViewState.State.LOADING
        getAllSavedPasswordsUseCase().handleResult(
            onSuccess = {
                if (it.isNotEmpty()) {
                    model.passwords = it
                    viewState.state.value = PasswordsViewState.State.IDLE
                    viewState.action.value = PasswordsViewState.Action.ShowSavedPasswordList(it)
                } else {
                    viewState.state.value = PasswordsViewState.State.EMPTY_LIST
                }
            },
            onError = {
                viewState.state.value = PasswordsViewState.State.ERROR
                viewState.action.value = PasswordsViewState.Action.GetSavedPasswordsError
            }
        )
    }

    private fun filterPassword(query: String) {
        viewState.state.value = PasswordsViewState.State.IDLE
        if (query.isEmpty()) {
            viewState.action.value =
                PasswordsViewState.Action.ShowSavedPasswordList(model.passwords)
            return
        }
        model.passwords.filter { password ->
            password.label.contains(query) || password.login?.contains(query) == true
        }.run {
            if (isEmpty()) {
                viewState.state.value = PasswordsViewState.State.EMPTY_LIST
                return
            }
            viewState.action.value = PasswordsViewState.Action.ShowSavedPasswordList(this)
        }
    }
}