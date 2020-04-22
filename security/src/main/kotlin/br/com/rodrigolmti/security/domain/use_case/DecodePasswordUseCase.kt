package br.com.rodrigolmti.security.domain.use_case

import android.util.Base64
import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.security.domain.error.SecurityError
import br.com.rodrigolmti.security.domain.repository.SecurityRepository
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

interface DecodePasswordUseCase {
    suspend operator fun invoke(password: String): Result<String, SecurityError>
}

class DecodePassword @Inject constructor(
    private val securityRepository: SecurityRepository
) : DecodePasswordUseCase {

    override suspend fun invoke(password: String): Result<String, SecurityError> {
        val key = Base64.decode(securityRepository.getUserKey(), Base64.DEFAULT)
        val vector = Base64.decode(securityRepository.getUserVector(), Base64.DEFAULT)

        return try {
            val ivParameterSpec = IvParameterSpec(vector)
            val secretKeySpec = SecretKeySpec(key, AES_ALGORITHM)
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
            val original = cipher.doFinal(Base64.decode(password, Base64.DEFAULT))
            Result.Success(String(original))
        } catch (ex: Exception) {
            Result.Error(SecurityError.DecodePasswordError)
        }
    }
}