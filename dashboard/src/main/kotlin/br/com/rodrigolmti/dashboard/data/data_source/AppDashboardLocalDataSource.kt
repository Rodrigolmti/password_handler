package br.com.rodrigolmti.dashboard.data.data_source

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.data.mapper.SavedPasswordEntityMapper
import br.com.rodrigolmti.dashboard.data.mapper.SavedPasswordModelMapper
import br.com.rodrigolmti.dashboard.domain.error.DashboardError
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.database.PasswordDatabase
import br.com.rodrigolmti.injector.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppDashboardLocalDataSource @Inject constructor(
    private val savedPasswordEntityMapper: SavedPasswordEntityMapper,
    private val savedPasswordModelMapper: SavedPasswordModelMapper,
    private val dispatcherProvider: DispatcherProvider,
    private val database: PasswordDatabase
) : DashboardLocalDataSource {

    override suspend fun deletePassword(model: SavedPasswordModel): Result<Unit, DashboardError> =
        withContext(dispatcherProvider.io()) {
            return@withContext try {
                val entity = savedPasswordEntityMapper.mapFrom(model)
                database.database().savedPasswordDao().delete(entity)
                Result.Success(Unit)
            } catch (error: Exception) {
                Result.Error(DashboardError.DeleteSavedPasswordError)
            }
        }

    override suspend fun savePassword(model: SavedPasswordModel): Result<Unit, DashboardError> =
        withContext(dispatcherProvider.io()) {
            return@withContext try {
                val entity = savedPasswordEntityMapper.mapFrom(model)
                database.database().savedPasswordDao().insert(entity)
                Result.Success(Unit)
            } catch (error: Exception) {
                Result.Error(DashboardError.InsertSavedPasswordError)
            }
        }

    override suspend fun updatePassword(model: SavedPasswordModel): Result<Unit, DashboardError> =
        withContext(dispatcherProvider.io()) {
            return@withContext try {
                val entity = savedPasswordEntityMapper.mapFrom(model)
                database.database().savedPasswordDao().update(entity)
                Result.Success(Unit)
            } catch (error: Exception) {
                Result.Error(DashboardError.UpdateSavedPasswordError)
            }
        }

    override suspend fun getAllSavedPasswords(): Result<List<SavedPasswordModel>, DashboardError> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val passwords = database.database().savedPasswordDao().getAll().map { entity ->
                    savedPasswordModelMapper.mapFrom(entity)
                }
                Result.Success(passwords)
            } catch (error: Exception) {
                Result.Error(DashboardError.GetAllSavedPasswordsError)
            }
        }
}