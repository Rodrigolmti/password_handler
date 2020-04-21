package br.com.rodrigolmti.password_keeper.data.data_source

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.password_keeper.domain.error.ApplicationError

interface KeyLocalDataSource {

    suspend fun saveUserKey(key: String): Result<Unit, ApplicationError>

    suspend fun getUserKey(): Result<String, ApplicationError>

    suspend fun saveUserVector(vector: String): Result<Unit, ApplicationError>

    suspend fun getUserVector(): Result<String, ApplicationError>
}