package br.com.rodrigolmti.security.data.repository

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.security.data.data_source.KeyLocalDataSource
import br.com.rodrigolmti.security.domain.error.SecurityError
import br.com.rodrigolmti.security.domain.repository.SecurityRepository
import javax.inject.Inject

class AppSecurityRepository @Inject constructor(
    private val localDataSource: KeyLocalDataSource
) : SecurityRepository {

    override suspend fun saveUserKey(key: String): Result<Unit, SecurityError> =
        localDataSource.saveUserKey(key)

    override suspend fun getUserKey(): String? = localDataSource.getUserKey()

    override suspend fun saveUserVector(vector: String): Result<Unit, SecurityError> =
        localDataSource.saveUserVector(vector)

    override suspend fun getUserVector(): String? = localDataSource.getUserVector()
}