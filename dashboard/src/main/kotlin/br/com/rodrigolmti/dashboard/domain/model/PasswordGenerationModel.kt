package br.com.rodrigolmti.dashboard.domain.model

data class PasswordGenerationModel(
    val passwordNumber: Int,
    val passwordLength: Int,
    val isUpperCase: Boolean,
    val isLowerCase: Boolean,
    val isNumbers: Boolean,
    val isSpecialChars: Boolean
)