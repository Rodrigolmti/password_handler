package br.com.rodrigolmti.dashboard.domain.use_cases

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.domain.error.DashboardError
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.domain.repository.DashboardRepository
import br.com.rodrigolmti.security.domain.use_case.EncodePasswordUseCase
import javax.inject.Inject

// TODO: Log events onError with the error
interface UpdateSavedPasswordUseCase {
    suspend operator fun invoke(model: SavedPasswordModel): Result<Unit, DashboardError>
}

class UpdateSavedPassword @Inject constructor(
    private val dashboardRepository: DashboardRepository,
    private val encodePasswordUseCase: EncodePasswordUseCase
) : UpdateSavedPasswordUseCase {

    override suspend fun invoke(model: SavedPasswordModel): Result<Unit, DashboardError> {
        encodePasswordUseCase(model.password).handleResult(onSuccess = { encoded ->
            dashboardRepository.updatePassword(model.copy(password = encoded)).handleResult(
                onSuccess = {
                    return Result.Success(Unit)
                },
                onError = {
                    return Result.Error(DashboardError.UpdateSavesPasswordError)
                }
            )
        }, onError = {
            return Result.Error(DashboardError.EncodePasswordError)
        })
        return Result.Error(DashboardError.UpdateSavesPasswordError)
    }
}