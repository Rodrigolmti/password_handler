package br.com.rodrigolmti.security.domain.use_case

import br.com.rodrigolmti.security.domain.repository.SecurityRepository
import javax.inject.Inject

interface UpdateUserBiometricUseCase {

    suspend operator fun invoke(checked: Boolean)
}

class UpdateUserBiometric @Inject constructor(
    private val securityRepository: SecurityRepository
) : UpdateUserBiometricUseCase {

    override suspend fun invoke(checked: Boolean) {
        securityRepository.updateUserBiometric(checked)
    }
}