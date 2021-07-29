package br.com.rodrigolmti.dashboard.domain.use_cases

import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.security.SecureRandom
import kotlin.test.assertTrue

internal class GeneratePasswordTest {

    private val useCase: GeneratePasswordUseCase = GeneratePassword()

    @Test
    fun execute_returnEmptyPassword() = runBlocking {
        val result = useCase(0, emptyList())

        assertTrue { result.isEmpty() }
    }

    @Test
    fun execute_returnRandomPassword() = runBlocking {
        val allowedChars: List<String> = listOf("A", "B", "C", "D", "E")
        val length = 10

        val result = useCase(length, allowedChars)

        assertTrue { result.isNotEmpty() && result.length == length }
    }
}