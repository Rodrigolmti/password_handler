package br.com.rodrigolmti.dashboard.data.data_source

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.dashboard.data.mapper.SavedPasswordEntityMapper
import br.com.rodrigolmti.dashboard.data.mapper.SavedPasswordModelMapper
import br.com.rodrigolmti.dashboard.domain.error.DashBoardError
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.database.PasswordDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppLocalDataSource @Inject constructor(
    private val savedPasswordEntityMapper: SavedPasswordEntityMapper,
    private val savedPasswordModelMapper: SavedPasswordModelMapper,
    private val database: PasswordDatabase
) : LocalDataSource {

    override suspend fun savePassword(model: SavedPasswordModel): Result<Unit, DashBoardError> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val entity = savedPasswordEntityMapper.mapFrom(model)
                database.database().savedPasswordDao().insert(entity)
                Result.Success(Unit)
            } catch (error: Exception) {
                Result.Error(DashBoardError.InsertSavedPasswordError)
            }
        }

    override suspend fun getAllSavedPasswords(): Result<List<SavedPasswordModel>, DashBoardError> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val passwords = database.database().savedPasswordDao().getAll().map { entity ->
                    savedPasswordModelMapper.mapFrom(entity)
                }
                Result.Success(passwords)
            } catch (error: Exception) {
                Result.Error(DashBoardError.InsertSavedPasswordError)
            }
        }
}