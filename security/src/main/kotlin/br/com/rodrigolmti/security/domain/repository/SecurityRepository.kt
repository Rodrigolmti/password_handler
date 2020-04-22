package br.com.rodrigolmti.security.domain.repository

import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.security.domain.error.SecurityError

interface SecurityRepository {

    suspend fun saveUserKey(key: String): Result<Unit, SecurityError>

    suspend fun getUserKey(): String?

    suspend fun saveUserVector(vector: String): Result<Unit, SecurityError>

    suspend fun getUserVector(): String?
}