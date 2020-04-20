package br.com.rodrigolmti.dashboard.ui.password

internal sealed class PasswordAction {

    data class CalculatePasswordStrength(val password: String) : PasswordAction()
    data class SavePassword(
        val id: Int? = null,
        val label: String,
        val login: String? = null,
        val password: String,
        val strength: Int
    ) : PasswordAction()
}