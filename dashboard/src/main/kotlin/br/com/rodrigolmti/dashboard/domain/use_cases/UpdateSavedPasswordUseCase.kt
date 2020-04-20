package br.com.rodrigolmti.dashboard.domain.use_cases

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.domain.error.DashBoardError
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.domain.repository.DashboardRepository
import javax.inject.Inject

interface UpdateSavedPasswordUseCase {
    suspend operator fun invoke(model: SavedPasswordModel): Result<Unit, DashBoardError>
}

class UpdateSavedPassword @Inject constructor(
    private val dashboardRepository: DashboardRepository
) : UpdateSavedPasswordUseCase {

    override suspend fun invoke(model: SavedPasswordModel): Result<Unit, DashBoardError> =
        dashboardRepository.updatePassword(model)
}