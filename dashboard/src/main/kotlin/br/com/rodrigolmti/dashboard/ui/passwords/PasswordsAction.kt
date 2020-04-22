package br.com.rodrigolmti.dashboard.ui.passwords

internal sealed class PasswordsAction {
    object Init : PasswordsAction()
    data class FilterPasswords(val query: String) : PasswordsAction()
}