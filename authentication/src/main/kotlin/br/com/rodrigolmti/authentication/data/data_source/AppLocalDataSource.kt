package br.com.rodrigolmti.authentication.data.data_source

import br.com.rodrigolmti.authentication.data.mapper.BackupPinEntityMapper
import br.com.rodrigolmti.authentication.domain.error.AuthenticationError
import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.database.PasswordDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppLocalDataSource @Inject constructor(
    private val database: PasswordDatabase,
    private val backupPinEntityMapper: BackupPinEntityMapper
) : LocalDataSource {

    override suspend fun deletePin(): Result<Unit, AuthenticationError> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                database.database().backupPinDao().delete()
                Result.Success(Unit)
            } catch (error: Exception) {
                Result.Error(AuthenticationError.DeletePinError)
            }
        }

    override suspend fun savePin(pin: String): Result<Unit, AuthenticationError> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val entity = backupPinEntityMapper.mapFrom(pin)
                database.database().backupPinDao().insert(entity)
                Result.Success(Unit)
            } catch (error: Exception) {
                Result.Error(AuthenticationError.DeletePinError)
            }
        }

    override suspend fun getPin(): Result<String, AuthenticationError> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val pins = database.database().backupPinDao().getPin()
                Result.Success(pins.first().pin)
            } catch (error: Exception) {
                Result.Error(AuthenticationError.DeletePinError)
            }
        }
}