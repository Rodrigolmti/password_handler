package br.com.rodrigolmti.dashboard.domain.use_cases

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.domain.error.DashboardError
import br.com.rodrigolmti.dashboard.domain.model.PasswordGenerationModel
import javax.inject.Inject
import kotlin.random.Random

private const val MAX_PASSWORD_LENGTH = 99
private const val NUMBER_OF_PASSWORD = 1

interface GenerateRandomPasswordUseCase {
    suspend operator fun invoke(): Result<String, DashboardError>
}

class GenerateRandomPassword @Inject constructor(
    private val generatePasswordUseCase: GeneratePasswordUseCase,
    private val getPasswordAllowedCharsUseCase: GetPasswordAllowedCharsUseCase
) : GenerateRandomPasswordUseCase {

    override suspend fun invoke(): Result<String, DashboardError> {
        return try {
            val model = generatePasswordModel()
            val allowedChars = getPasswordAllowedCharsUseCase(model)
            val password = generatePasswordUseCase(model.passwordLength, allowedChars)
            Result.Success(password)
        } catch (error: Exception) {
            Result.Error(DashboardError.GeneratePasswordError)
        }
    }

    private fun generatePasswordModel(): PasswordGenerationModel {
        val model = PasswordGenerationModel(
            isSpecialChars = Random.nextBoolean(),
            isNumbers = Random.nextBoolean(),
            isLowerCase = Random.nextBoolean(),
            isUpperCase = Random.nextBoolean(),
            passwordNumber = NUMBER_OF_PASSWORD,
            passwordLength = Random.nextInt(MAX_PASSWORD_LENGTH)
        )

        if (!model.isLowerCase &&
            !model.isNumbers &&
            !model.isLowerCase &&
            !model.isUpperCase &&
            model.passwordLength <= 0
        ) {
            generatePasswordModel()
        }

        return model
    }
}