package br.com.rodrigolmti.dashboard.domain.model

data class PasswordGeneratorModel(
    val passwordNumber: Int,
    val passwordLength: Int,
    val isUpperCase: Boolean,
    val isLowerCase: Boolean,
    val isNumbers: Boolean,
    val isSpecialChars: Boolean
)