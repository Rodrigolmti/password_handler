package br.com.rodrigolmti.password_keeper.data.repository

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.password_keeper.data.data_source.KeyLocalDataSource
import br.com.rodrigolmti.password_keeper.domain.error.ApplicationError
import br.com.rodrigolmti.password_keeper.domain.repository.KeyRepository
import javax.inject.Inject

class AppKeyRepository @Inject constructor(
    private val localDataSource: KeyLocalDataSource
) : KeyRepository {

    override suspend fun saveUserKey(key: String): Result<Unit, ApplicationError> =
        localDataSource.saveUserKey(key)

    override suspend fun getUserKey(): Result<String, ApplicationError> =
        localDataSource.getUserKey()

    override suspend fun saveUserVector(vector: String): Result<Unit, ApplicationError> =
        localDataSource.saveUserVector(vector)

    override suspend fun getUserVector(): Result<String, ApplicationError> =
        localDataSource.getUserVector()
}