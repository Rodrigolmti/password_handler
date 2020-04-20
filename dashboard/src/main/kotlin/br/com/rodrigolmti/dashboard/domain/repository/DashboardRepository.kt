package br.com.rodrigolmti.dashboard.domain.repository

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.domain.error.DashboardError
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel

interface DashboardRepository {

    suspend fun deletePassword(model: SavedPasswordModel): Result<Unit, DashboardError>

    suspend fun savePassword(model: SavedPasswordModel): Result<Unit, DashboardError>

    suspend fun updatePassword(model: SavedPasswordModel): Result<Unit, DashboardError>

    suspend fun getAllSavedPasswords(): Result<List<SavedPasswordModel>, DashboardError>
}