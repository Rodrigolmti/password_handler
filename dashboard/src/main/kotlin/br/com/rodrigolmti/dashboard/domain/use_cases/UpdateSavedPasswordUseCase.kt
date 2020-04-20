package br.com.rodrigolmti.dashboard.domain.use_cases

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.domain.error.DashboardError
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.domain.repository.DashboardRepository
import javax.inject.Inject

interface UpdateSavedPasswordUseCase {
    suspend operator fun invoke(model: SavedPasswordModel): Result<Unit, DashboardError>
}

class UpdateSavedPassword @Inject constructor(
    private val dashboardRepository: DashboardRepository
) : UpdateSavedPasswordUseCase {

    override suspend fun invoke(model: SavedPasswordModel): Result<Unit, DashboardError> =
        dashboardRepository.updatePassword(model)
}