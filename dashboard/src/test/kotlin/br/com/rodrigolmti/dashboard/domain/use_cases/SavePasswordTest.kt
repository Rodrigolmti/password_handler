package br.com.rodrigolmti.dashboard.domain.use_cases

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.domain.error.DashboardError
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.domain.repository.DashboardRepository
import br.com.rodrigolmti.security.domain.error.SecurityError
import br.com.rodrigolmti.security.domain.use_case.EncodePasswordUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

internal class SavePasswordTest {

    private val dashboardRepository: DashboardRepository = mockk()
    private val encodePasswordUseCase: EncodePasswordUseCase = mockk()

    private val model = SavedPasswordModel(
        id = 1,
        password = "3233ff",
        strength = 10,
        login = "jorvis@gmail.com",
        label = "google"
    )

    private val useCase = SavePassword(
        dashboardRepository,
        encodePasswordUseCase
    )

    @Test
    fun call_encode_password_return_error() = runBlocking {
        coEvery {
            encodePasswordUseCase(model.password)
        } returns Result.Error(SecurityError.SaveVectorError)

        val response = useCase(model)

        coVerify(exactly = 1) { encodePasswordUseCase(model.password) }
        assert(response is Result.Error && response.value == DashboardError.EncodePasswordError)
    }

    @Test
    fun call_encode_password_return_success() = runBlocking {
        val encoded = "encoded"
        coEvery { encodePasswordUseCase(model.password) } returns Result.Success(encoded)
        coEvery {
            dashboardRepository.savePassword(model.copy(password = encoded))
        } returns Result.Success(Unit)

        useCase(model)

        coVerify(exactly = 1) { encodePasswordUseCase(model.password) }
        coVerify(exactly = 1) { dashboardRepository.savePassword(model.copy(password = encoded)) }
    }

    @Test
    fun call_save_password_return_error() = runBlocking {
        val encoded = "encoded"
        coEvery { encodePasswordUseCase(model.password) } returns Result.Success(encoded)
        coEvery {
            dashboardRepository.savePassword(model.copy(password = encoded))
        } returns Result.Error(DashboardError.InsertSavedPasswordError)

        val response = useCase(model)

        assert(
            response is Result.Error && response.value == DashboardError.InsertSavedPasswordError
        )
    }

    @Test
    fun call_save_password_return_success() = runBlocking {
        val encoded = "encoded"
        coEvery { encodePasswordUseCase(model.password) } returns Result.Success(encoded)
        coEvery {
            dashboardRepository.savePassword(model.copy(password = encoded))
        } returns Result.Success(Unit)

        val response = useCase(model)

        assert(response is Result.Success)
    }
}