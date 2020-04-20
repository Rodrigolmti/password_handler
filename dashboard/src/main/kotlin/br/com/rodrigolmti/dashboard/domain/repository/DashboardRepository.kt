package br.com.rodrigolmti.dashboard.domain.repository

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.domain.error.DashBoardError
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel

interface DashboardRepository {

    suspend fun savePassword(model: SavedPasswordModel): Result<Unit, DashBoardError>

    suspend fun getAllSavedPasswords(): Result<List<SavedPasswordModel>, DashBoardError>
}