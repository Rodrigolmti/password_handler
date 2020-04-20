package br.com.rodrigolmti.dashboard.domain.use_cases

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.domain.error.DashboardError
import br.com.rodrigolmti.dashboard.domain.model.PasswordGenerationModel
import br.com.rodrigolmti.dashboard.domain.model.PasswordModel
import javax.inject.Inject

interface GeneratePreDeterminedPasswordUseCase {
    suspend operator fun invoke(
        model: PasswordGenerationModel
    ): Result<List<PasswordModel>, DashboardError>
}

class GeneratePreDeterminedPassword @Inject constructor(
    private val passwordStrengthUseCase: PasswordStrengthUseCase,
    private val generatePasswordUseCase: GeneratePasswordUseCase,
    private val getPasswordAllowedCharsUseCase: GetPasswordAllowedCharsUseCase
) : GeneratePreDeterminedPasswordUseCase {

    override suspend fun invoke(
        model: PasswordGenerationModel
    ): Result<List<PasswordModel>, DashboardError> {
        return try {
            val allowedChars: List<String> = getPasswordAllowedCharsUseCase(model)
            val passwords: MutableList<PasswordModel> = mutableListOf()
            for (i: Int in 1..model.passwordNumber) {

                val password = generatePasswordUseCase(
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
            Result.Error(DashboardError.GeneratePasswordError)
        }
    }
}