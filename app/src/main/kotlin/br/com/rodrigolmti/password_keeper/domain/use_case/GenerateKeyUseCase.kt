package br.com.rodrigolmti.password_keeper.domain.use_case

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.password_keeper.domain.error.ApplicationError
import java.security.SecureRandom
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject

private const val KEY_LENGTH = 256
private const val ALGORITHM = "AES"

interface GenerateKeyUseCase {
    suspend operator fun invoke(): Result<SecretKey, ApplicationError>
}

class GenerateKey @Inject constructor() : GenerateKeyUseCase {

    override suspend fun invoke(): Result<SecretKey, ApplicationError> {
        return try {
            val outputKeyLength = KEY_LENGTH
            val secureRandom = SecureRandom()
            val keyGenerator: KeyGenerator = KeyGenerator.getInstance(ALGORITHM)
            keyGenerator.init(outputKeyLength, secureRandom)
            Result.Success(keyGenerator.generateKey())
        } catch (error: Exception) {
            Result.Error(ApplicationError.GenerateKeyError)
        }
    }
}