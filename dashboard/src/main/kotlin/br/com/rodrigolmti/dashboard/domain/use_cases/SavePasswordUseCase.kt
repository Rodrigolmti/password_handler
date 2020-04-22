package br.com.rodrigolmti.dashboard.domain.use_cases

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.domain.error.DashboardError
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.domain.repository.DashboardRepository
import br.com.rodrigolmti.security.domain.use_case.EncodePasswordUseCase
import javax.inject.Inject

interface SavePasswordUseCase {
    suspend operator fun invoke(model: SavedPasswordModel): Result<Unit, DashboardError>
}

class SavePassword @Inject constructor(
    private val dashboardRepository: DashboardRepository,
    private val encodePasswordUseCase: EncodePasswordUseCase
) : SavePasswordUseCase {

    override suspend fun invoke(model: SavedPasswordModel): Result<Unit, DashboardError> {
        encodePasswordUseCase(model.password).handleResult(onSuccess = { encoded ->
            dashboardRepository.savePassword(model.copy(password = encoded)).handleResult(
                onSuccess = {
                    return Result.Success(Unit)
                },
                onError = {
                    return Result.Error(DashboardError.InsertSavedPasswordError)
                }
            )
        }, onError = {
            return Result.Error(DashboardError.EncodePasswordError)
        })
        return Result.Error(DashboardError.InsertSavedPasswordError)
    }
}