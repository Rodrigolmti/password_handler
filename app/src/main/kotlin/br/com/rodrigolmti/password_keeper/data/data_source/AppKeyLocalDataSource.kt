package br.com.rodrigolmti.password_keeper.data.data_source

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.password_keeper.domain.error.ApplicationError
import br.com.rodrigolmti.user_preferences.USER_SALT
import br.com.rodrigolmti.user_preferences.USER_VECTOR
import br.com.rodrigolmti.user_preferences.UserPreferences
import javax.inject.Inject

class AppKeyLocalDataSource @Inject constructor(
    private val userPreferences: UserPreferences
) : KeyLocalDataSource {

    override suspend fun saveUserKey(key: String): Result<Unit, ApplicationError> {
        return if (userPreferences.putString(USER_SALT, key)) {
            Result.Success(Unit)
        } else {
            Result.Error(ApplicationError.SaveKeyError)
        }
    }

    override suspend fun getUserKey(): Result<String, ApplicationError> {
        return userPreferences.getString(USER_SALT)?.let { key ->
            Result.Success(key)
        } ?: run {
            Result.Error(ApplicationError.GetKeyError)
        }
    }

    override suspend fun saveUserVector(vector: String): Result<Unit, ApplicationError> {
        return if (userPreferences.putString(USER_VECTOR, vector)) {
            Result.Success(Unit)
        } else {
            Result.Error(ApplicationError.SaveKeyError)
        }
    }

    override suspend fun getUserVector(): Result<String, ApplicationError> {
        return userPreferences.getString(USER_VECTOR)?.let { vector ->
            Result.Success(vector)
        } ?: run {
            Result.Error(ApplicationError.GetVectorError)
        }
    }
}