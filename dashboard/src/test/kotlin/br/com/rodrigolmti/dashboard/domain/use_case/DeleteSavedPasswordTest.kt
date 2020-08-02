package br.com.rodrigolmti.dashboard.domain.use_case

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.domain.error.DashboardError
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.domain.repository.DashboardRepository
import br.com.rodrigolmti.dashboard.domain.use_cases.DeleteSavedPassword
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteSavedPasswordTest {

    private val dashboardRepository: DashboardRepository = mockk()
    private val useCase = DeleteSavedPassword(dashboardRepository)

    private val model = SavedPasswordModel(
        id = 1,
        password = "3233ff",
        strength = 10,
        login = "jorvis@gmail.com",
        label = "google"
    )

    @Test
    fun call_delete_password_return_success() = runBlocking {
        val result = Result.Success(Unit)
        coEvery { dashboardRepository.deletePassword(model) } returns result

        val response = useCase(model)

        coVerify(exactly = 1) { dashboardRepository.deletePassword(model) }
        assert(response is Result.Success)
    }

    @Test
    fun call_delete_password_return_error() = runBlocking {
        val result = Result.Error(DashboardError.DeleteSavedPasswordError)
        coEvery { dashboardRepository.deletePassword(model) } returns result

        val response = useCase(model)

        coVerify(exactly = 1) { dashboardRepository.deletePassword(model) }
        assert(response is Result.Error && response.value == DashboardError.DeleteSavedPasswordError)
    }
}
