package br.com.rodrigolmti.security.domain.use_case

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.security.domain.error.SecurityError
import java.security.SecureRandom
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject

private const val VECTOR_SIZE = 16

interface GenerateVectorUseCase {
    suspend operator fun invoke(): Result<IvParameterSpec, SecurityError>
}

class GenerateVector @Inject constructor() :
    GenerateVectorUseCase {
    override suspend fun invoke(): Result<IvParameterSpec, SecurityError> {
        return try {
            val ivRandom = SecureRandom()
            val iv = ByteArray(VECTOR_SIZE)
            ivRandom.nextBytes(iv)
            Result.Success(IvParameterSpec(iv))
        } catch (error: Exception) {
            Result.Error(SecurityError.GenerateVectorError)
        }
    }
}