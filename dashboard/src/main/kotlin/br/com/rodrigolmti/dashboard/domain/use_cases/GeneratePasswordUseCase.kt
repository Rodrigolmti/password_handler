package br.com.rodrigolmti.dashboard.domain.use_cases

import java.security.SecureRandom
import javax.inject.Inject

interface GeneratePasswordUseCase {
    suspend operator fun invoke(
        length: Int,
        allowedChars: List<String>
    ): String
}

class GeneratePassword @Inject constructor() : GeneratePasswordUseCase {

    override suspend fun invoke(length: Int, allowedChars: List<String>): String {
        val random = SecureRandom()
        val stringBuilder = StringBuilder(length)
        for (i in 0 until length) {
            val index = random.nextInt(allowedChars.size - 1)
            val string = allowedChars[index]
            stringBuilder.append(string)
        }
        return stringBuilder.toString()
    }
}