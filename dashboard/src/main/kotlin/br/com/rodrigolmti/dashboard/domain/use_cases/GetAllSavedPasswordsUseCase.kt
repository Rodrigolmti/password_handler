package br.com.rodrigolmti.dashboard.domain.use_cases

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.domain.error.DashboardError
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.domain.repository.DashboardRepository
import br.com.rodrigolmti.security.domain.use_case.DecodePasswordUseCase
import javax.inject.Inject

interface GetAllSavedPasswordsUseCase {
    suspend operator fun invoke(): Result<List<SavedPasswordModel>, DashboardError>
}

class GetAllSavedPasswords @Inject constructor(
    private val dashboardRepository: DashboardRepository,
    private val decodePasswordUseCase: DecodePasswordUseCase
) : GetAllSavedPasswordsUseCase {

    override suspend fun invoke(): Result<List<SavedPasswordModel>, DashboardError> {
        val passwords = dashboardRepository.getAllSavedPasswords().handleResult()
        passwords?.map { password ->
            password.copy(
                password = decodePasswordUseCase(password.password).handleResult()
                    ?: password.password
            )
        }?.let {
            return Result.Success(it)
        }
        return Result.Error(DashboardError.GetAllSavedPasswordsError)
    }
}