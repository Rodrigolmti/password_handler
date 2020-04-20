package br.com.rodrigolmti.dashboard.data.repository

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.data.data_source.LocalDataSource
import br.com.rodrigolmti.dashboard.domain.error.DashboardError
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.domain.repository.DashboardRepository
import javax.inject.Inject

class AppDashboardRepository @Inject constructor(
    private val localDataSource: LocalDataSource
) : DashboardRepository {

    override suspend fun deletePassword(model: SavedPasswordModel): Result<Unit, DashboardError> =
        localDataSource.deletePassword(model)

    override suspend fun savePassword(model: SavedPasswordModel): Result<Unit, DashboardError> =
        localDataSource.savePassword(model)

    override suspend fun updatePassword(model: SavedPasswordModel): Result<Unit, DashboardError> =
        localDataSource.updatePassword(model)

    override suspend fun getAllSavedPasswords(): Result<List<SavedPasswordModel>, DashboardError> =
        localDataSource.getAllSavedPasswords()
}