package br.com.rodrigolmti.security.data.data_source

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.security.domain.error.SecurityError

interface KeyLocalDataSource {

    suspend fun saveUserKey(key: String): Result<Unit, SecurityError>

    suspend fun getUserKey(): String?

    suspend fun saveUserVector(vector: String): Result<Unit, SecurityError>

    suspend fun getUserVector(): String?
}