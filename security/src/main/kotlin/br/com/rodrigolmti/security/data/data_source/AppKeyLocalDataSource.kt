package br.com.rodrigolmti.security.data.data_source

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.security.domain.error.SecurityError
import br.com.rodrigolmti.user_preferences.USER_BIOMETRIC
import br.com.rodrigolmti.user_preferences.USER_SALT
import br.com.rodrigolmti.user_preferences.USER_VECTOR
import br.com.rodrigolmti.user_preferences.UserPreferences
import javax.inject.Inject

class AppKeyLocalDataSource @Inject constructor(
    private val userPreferences: UserPreferences
) : KeyLocalDataSource {

    override suspend fun saveUserKey(key: String): Result<Unit, SecurityError> {
        return if (userPreferences.putString(USER_SALT, key)) {
            Result.Success(Unit)
        } else {
            Result.Error(SecurityError.SaveKeyError)
        }
    }

    override suspend fun getUserKey(): String? = userPreferences.getString(USER_SALT)

    override suspend fun saveUserVector(vector: String): Result<Unit, SecurityError> {
        return if (userPreferences.putString(USER_VECTOR, vector)) {
            Result.Success(Unit)
        } else {
            Result.Error(SecurityError.SaveVectorError)
        }
    }

    override suspend fun getUserVector(): String? = userPreferences.getString(USER_VECTOR)

    override suspend fun updateUserBiometric(checked: Boolean): Boolean =
        userPreferences.putBoolean(
            USER_BIOMETRIC, checked
        )

    override suspend fun getUserBiometric(): Boolean = userPreferences.getBoolean(USER_BIOMETRIC)
}