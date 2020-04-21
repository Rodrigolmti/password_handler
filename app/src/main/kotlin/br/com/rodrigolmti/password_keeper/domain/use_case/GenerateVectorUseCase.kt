package br.com.rodrigolmti.password_keeper.domain.use_case

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.password_keeper.domain.error.ApplicationError
import java.security.SecureRandom
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject

private const val VECTOR_SIZE = 16

interface GenerateVectorUseCase {
    suspend operator fun invoke(): Result<IvParameterSpec, ApplicationError>
}

class GenerateVector @Inject constructor() : GenerateVectorUseCase {
    override suspend fun invoke(): Result<IvParameterSpec, ApplicationError> {
        return try {
            val ivRandom = SecureRandom()
            val iv = ByteArray(VECTOR_SIZE)
            ivRandom.nextBytes(iv)
            Result.Success(IvParameterSpec(iv))
        } catch (error: Exception) {
            Result.Error(ApplicationError.GenerateVectorError)
        }
    }
}