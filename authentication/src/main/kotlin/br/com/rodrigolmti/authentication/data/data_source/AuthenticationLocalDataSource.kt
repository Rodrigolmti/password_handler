package br.com.rodrigolmti.authentication.data.data_source

import br.com.rodrigolmti.authentication.domain.error.AuthenticationError
import br.com.rodrigolmti.core_android.Result

interface AuthenticationLocalDataSource {

    suspend fun deletePin(): Result<Unit, AuthenticationError>

    suspend fun savePin(pin: String): Result<Unit, AuthenticationError>

    suspend fun getPin(): Result<String, AuthenticationError>
}