package br.com.rodrigolmti.security.domain.use_case

import android.util.Base64
import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.security.domain.error.SecurityError
import br.com.rodrigolmti.security.domain.repository.SecurityRepository
import javax.inject.Inject

interface GenerateKeysUseCase {
    suspend operator fun invoke(): Result<Unit, SecurityError>
}

class GenerateKeys @Inject constructor(
    private val generateSecretKeyUseCase: GenerateSecretKeyUseCase,
    private val generateVectorUseCase: GenerateVectorUseCase,
    private val securityRepository: SecurityRepository
) : GenerateKeysUseCase {

    override suspend fun invoke(): Result<Unit, SecurityError> {
        return try {

            if (securityRepository.getUserKey() != null && securityRepository.getUserVector() != null) {
                return Result.Success(Unit)
            }

            generateSecretKeyUseCase().handleResult(onSuccess = { key ->
                val encodedKey: String = Base64.encodeToString(key.encoded, Base64.DEFAULT)
                securityRepository.saveUserKey(encodedKey)
            }, onError = {
                return Result.Error(SecurityError.GenerateKeysError)
            })

            generateVectorUseCase().handleResult(onSuccess = { vector ->
                val encodedVector: String = Base64.encodeToString(vector.iv, Base64.DEFAULT)
                securityRepository.saveUserVector(encodedVector)
            }, onError = {
                return Result.Error(SecurityError.GenerateKeysError)
            })

            Result.Success(Unit)

        } catch (error: Exception) {
            Result.Error(SecurityError.GenerateKeysError)
        }
    }
}