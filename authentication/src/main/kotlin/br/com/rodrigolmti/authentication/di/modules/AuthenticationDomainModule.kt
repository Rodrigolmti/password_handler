package br.com.rodrigolmti.authentication.di.modules

import br.com.rodrigolmti.authentication.data.repository.AppAuthenticationRepository
import br.com.rodrigolmti.authentication.domain.repository.AuthenticationRepository
import br.com.rodrigolmti.authentication.domain.use_cases.CheckPin
import br.com.rodrigolmti.authentication.domain.use_cases.CheckPinUseCase
import br.com.rodrigolmti.authentication.domain.use_cases.SavePin
import br.com.rodrigolmti.authentication.domain.use_cases.SavePinUseCase
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
internal abstract class AuthenticationDomainModule {

    @[Binds Reusable]
    abstract fun bindsAppDashboardRepository(
        appAuthenticationRepository: AppAuthenticationRepository
    ): AuthenticationRepository

    @[Binds Reusable]
    abstract fun bindsCheckPinUseCase(
        checkPin: CheckPin
    ): CheckPinUseCase

    @[Binds Reusable]
    abstract fun bindsSavePinUseCase(
        savePin: SavePin
    ): SavePinUseCase
}