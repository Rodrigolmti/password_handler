package br.com.rodrigolmti.security.domain.use_case

import android.util.Base64
import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.security.domain.error.SecurityError
import br.com.rodrigolmti.security.domain.repository.SecurityRepository
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

const val AES_ALGORITHM = "AES"
const val TRANSFORMATION = "AES/CBC/PKCS5PADDING"

interface EncodePasswordUseCase {
    suspend operator fun invoke(password: String): Result<String, SecurityError>
}

class EncodePassword @Inject constructor(
    private val securityRepository: SecurityRepository
) : EncodePasswordUseCase {

    override suspend fun invoke(password: String): Result<String, SecurityError> {
        val key = Base64.decode(securityRepository.getUserKey(), Base64.DEFAULT)
        val vector = Base64.decode(securityRepository.getUserVector(), Base64.DEFAULT)

        return try {
            val ivParameterSpec = IvParameterSpec(vector)
            val secretKeySpec = SecretKeySpec(key, AES_ALGORITHM)
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
            val encrypted = cipher.doFinal(password.toByteArray())
            Result.Success(Base64.encodeToString(encrypted, Base64.DEFAULT))
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            Result.Error(SecurityError.EncodePasswordError)
        }
    }
}