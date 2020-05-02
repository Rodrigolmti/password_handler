package br.com.rodrigolmti.dashboard.domain.use_cases

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.domain.error.DashboardError
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.domain.repository.DashboardRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertTrue

internal class DeleteSavedPasswordTest {

    private val repository: DashboardRepository = mockk()
    private val useCase: DeleteSavedPasswordUseCase = DeleteSavedPassword(repository)

    private val model: SavedPasswordModel = mockk()

    @Test
    fun execute_callRepositoryMethod() = runBlocking {
        prepareScenario(Result.Success(Unit))

        useCase(model)

        coVerify(exactly = 1) { repository.deletePassword(model) }
    }

    @Test
    fun execute_callRepositoryMethod_returnSuccess() = runBlocking {
        prepareScenario(Result.Success(Unit))

        val result = useCase(model)

        assertTrue { result is Result.Success }
    }

    @Test
    fun execute_callRepositoryMethod_returnError() = runBlocking {
        prepareScenario(Result.Error(DashboardError.DeleteSavedPasswordError))

        val result = useCase(model)

        assertTrue {
            result is Result.Error && result.value == DashboardError.DeleteSavedPasswordError
        }
    }

    private fun prepareScenario(result: Result<Unit, DashboardError>) {
        coEvery {
            repository.deletePassword(any())
        } answers {
            result
        }
    }
}