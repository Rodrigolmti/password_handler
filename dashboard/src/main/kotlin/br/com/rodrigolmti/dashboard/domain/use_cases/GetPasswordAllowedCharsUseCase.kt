package br.com.rodrigolmti.dashboard.domain.use_cases

import br.com.rodrigolmti.dashboard.domain.model.PasswordGenerationModel
import java.util.*
import javax.inject.Inject

private const val LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz"
private const val NUMBERS = "0123456789"
private const val CHARS = "!@#\$%&*()_+-=[]?"

interface GetPasswordAllowedCharsUseCase {
    suspend operator fun invoke(
        model: PasswordGenerationModel
    ): List<String>
}

class GetPasswordAllowedChars @Inject constructor() : GetPasswordAllowedCharsUseCase {

    override suspend fun invoke(model: PasswordGenerationModel): List<String> {
        val upperCaseLetter = LOWER_CASE_LETTERS.toUpperCase(Locale.getDefault())

        val stringBuilder = java.lang.StringBuilder()
        if (model.isLowerCase) {
            stringBuilder.append(LOWER_CASE_LETTERS)
        }
        if (model.isUpperCase) {
            stringBuilder.append(upperCaseLetter)
        }
        if (model.isNumbers) {
            stringBuilder.append(NUMBERS)
        }
        if (model.isSpecialChars) {
            stringBuilder.append(CHARS)
        }

        return stringBuilder.toString().split("").shuffled()
    }
}