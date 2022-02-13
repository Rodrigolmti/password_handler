package br.com.rodrigolmti.authentication.domain.use_cases

import br.com.rodrigolmti.authentication.domain.error.AuthenticationError
import br.com.rodrigolmti.authentication.domain.repository.AuthenticationRepository
import br.com.rodrigolmti.core_android.Result
import br.com.rodrigolmti.security.domain.use_case.EncodePasswordUseCase
import javax.inject.Inject

interface SavePinUseCase {
    suspend operator fun invoke(pin: String): Result<Unit, AuthenticationError>
}

class SavePin @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val encodePasswordUseCase: EncodePasswordUseCase
) : SavePinUseCase {

    override suspend fun invoke(pin: String): Result<Unit, AuthenticationError> {
        encodePasswordUseCase(pin).handleResult(onSuccess = { encoded ->
            authenticationRepository.savePin(encoded).handleResult(
                onSuccess = {
                    return Result.Success(Unit)
                },
                onError = {
                    return Result.Error(AuthenticationError.SavePinError)
                }
            )
        }, onError = {
            return Result.Error(AuthenticationError.EncodePasswordError)
        })
        return Result.Error(AuthenticationError.SavePinError)
    }
}