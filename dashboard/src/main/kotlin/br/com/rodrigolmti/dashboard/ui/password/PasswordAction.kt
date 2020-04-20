package br.com.rodrigolmti.dashboard.ui.password

internal sealed class PasswordAction {

    data class CalculatePasswordStrength(val password: String) : PasswordAction()
}