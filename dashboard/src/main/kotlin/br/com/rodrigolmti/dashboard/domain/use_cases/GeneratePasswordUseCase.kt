package br.com.rodrigolmti.dashboard.domain.use_cases

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.domain.error.DashBoardError
import br.com.rodrigolmti.dashboard.domain.model.PasswordGeneratorModel
import br.com.rodrigolmti.dashboard.domain.model.PasswordModel
import java.security.SecureRandom
import java.util.*
import javax.inject.Inject

private const val LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz"
private const val NUMBERS = "0123456789"
private const val CHARS = "!@#\$%&*()_+-=[]?"

interface GeneratePasswordUseCase {
    suspend operator fun invoke(
        model: PasswordGeneratorModel
    ): Result<List<PasswordModel>, DashBoardError>
}

class GeneratePassword @Inject constructor(
    private val passwordStrengthUseCase: PasswordStrengthUseCase
) : GeneratePasswordUseCase {

    override suspend fun invoke(
        model: PasswordGeneratorModel
    ): Result<List<PasswordModel>, DashBoardError> {
        return try {
            val allowedChars: List<String> = generateAllowedCharacters(model)
            val passwords: MutableList<PasswordModel> = mutableListOf()
            for (i: Int in 1..model.passwordNumber) {

                val password = generatePassword(
                    model.passwordLength,
                    allowedChars
                )

                passwords.add(
                    PasswordModel(
                        password = password,
                        score = passwordStrengthUseCase(password)
                    )
                )
            }
            Result.Success(passwords)
        } catch (error: Exception) {
            Result.Error(DashBoardError.GeneratePasswordError)
        }
    }

    private fun generateAllowedCharacters(model: PasswordGeneratorModel): List<String> {
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

    private fun generatePassword(
        length: Int,
        allowedChars: List<String>
    ): String {
        val random = SecureRandom()
        val stringBuilder = StringBuilder(length)
        for (i in 0 until length) {
            val index = random.nextInt(allowedChars.size)
            val string = allowedChars[index]
            stringBuilder.append(string)
        }
        return stringBuilder.toString()
    }
}