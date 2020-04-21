package br.com.rodrigolmti.password_keeper.domain.repository

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.password_keeper.domain.error.ApplicationError

interface KeyRepository {

    suspend fun saveUserKey(key: String): Result<Unit, ApplicationError>

    suspend fun getUserKey(): Result<String, ApplicationError>

    suspend fun saveUserVector(vector: String): Result<Unit, ApplicationError>

    suspend fun getUserVector(): Result<String, ApplicationError>
}