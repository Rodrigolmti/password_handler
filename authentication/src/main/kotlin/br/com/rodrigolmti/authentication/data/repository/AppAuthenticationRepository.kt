package br.com.rodrigolmti.authentication.data.repository

import br.com.rodrigolmti.authentication.data.data_source.AuthenticationLocalDataSource
import br.com.rodrigolmti.authentication.domain.error.AuthenticationError
import br.com.rodrigolmti.authentication.domain.repository.AuthenticationRepository
import br.com.rodrigolmti.core_android.Result
import javax.inject.Inject

class AppAuthenticationRepository @Inject constructor(
    private val authenticationLocalDataSource: AuthenticationLocalDataSource
) : AuthenticationRepository {

    override suspend fun deletePin(): Result<Unit, AuthenticationError> =
        authenticationLocalDataSource.deletePin()

    override suspend fun savePin(pin: String): Result<Unit, AuthenticationError> =
        authenticationLocalDataSource.savePin(pin)

    override suspend fun getPin(): Result<String, AuthenticationError> = authenticationLocalDataSource.getPin()
}