package br.com.rodrigolmti.security.domain.use_case

import br.com.rodrigolmti.security.domain.repository.SecurityRepository
import javax.inject.Inject

interface GetUserBiometricUseCase {
    suspend operator fun invoke(): Boolean
}

class GetUserBiometric @Inject constructor(
    private val securityRepository: SecurityRepository
) : GetUserBiometricUseCase {

    override suspend fun invoke(): Boolean = securityRepository.getUserBiometric()
}