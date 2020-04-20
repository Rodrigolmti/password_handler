package br.com.rodrigolmti.dashboard.domain.use_cases

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.domain.error.DashBoardError
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.domain.repository.DashboardRepository
import javax.inject.Inject

interface GetAllSavedPasswordsUseCase {
    suspend operator fun invoke() : Result<List<SavedPasswordModel>, DashBoardError>
}

class GetAllSavedPasswords @Inject constructor(
    private val dashboardRepository: DashboardRepository
) : GetAllSavedPasswordsUseCase {

    override suspend fun invoke(): Result<List<SavedPasswordModel>, DashBoardError> =
        dashboardRepository.getAllSavedPasswords()
}