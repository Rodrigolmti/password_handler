package br.com.rodrigolmti.authentication.domain.use_cases

import br.com.rodrigolmti.authentication.domain.error.AuthenticationError
import br.com.rodrigolmti.authentication.domain.repository.AuthenticationRepository
import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.security.domain.use_case.DecodePasswordUseCase
import javax.inject.Inject

interface CheckPinUseCase {
    suspend operator fun invoke(pin: String): Result<Boolean, AuthenticationError>
}

class CheckPin @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val decodePassword: DecodePasswordUseCase,
) : CheckPinUseCase {

    override suspend fun invoke(pin: String): Result<Boolean, AuthenticationError> {
        authenticationRepository.getPin().handleResult(
            onSuccess = {
                decodePassword(it).handleResult(
                    onSuccess = { decoded ->
                        return Result.Success(decoded == pin)
                    },
                    onError = {
                        return Result.Error(AuthenticationError.DecodePasswordError)
                    }
                )
            },
            onError = {
                return Result.Error(AuthenticationError.CheckPinError)
            }
        )
        return Result.Error(AuthenticationError.CheckPinError)
    }
}