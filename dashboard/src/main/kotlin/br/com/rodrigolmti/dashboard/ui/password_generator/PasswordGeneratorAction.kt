package br.com.rodrigolmti.dashboard.ui.password_generator

sealed class PasswordGeneratorAction {
    data class GeneratePassword(
        val passwordLength: Int,
        val passwordNumber: Int,
        val isUpperCase: Boolean,
        val isLowerCase: Boolean,
        val isNumbers: Boolean,
        val isSpecialChars: Boolean
    ) : PasswordGeneratorAction()
}