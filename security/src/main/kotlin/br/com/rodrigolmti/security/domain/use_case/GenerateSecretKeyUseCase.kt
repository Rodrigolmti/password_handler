package br.com.rodrigolmti.security.domain.use_case

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.security.domain.error.SecurityError
import java.security.SecureRandom
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject

private const val KEY_LENGTH = 256

interface GenerateSecretKeyUseCase {
    suspend operator fun invoke(): Result<SecretKey, SecurityError>
}

class GenerateSecretKey @Inject constructor() :
    GenerateSecretKeyUseCase {

    override suspend fun invoke(): Result<SecretKey, SecurityError> {
        return try {
            val outputKeyLength =
                KEY_LENGTH
            val secureRandom = SecureRandom()
            val keyGenerator: KeyGenerator = KeyGenerator.getInstance(AES_ALGORITHM)
            keyGenerator.init(outputKeyLength, secureRandom)
            Result.Success(keyGenerator.generateKey())
        } catch (error: Exception) {
            Result.Error(SecurityError.GenerateKeyError)
        }
    }
}