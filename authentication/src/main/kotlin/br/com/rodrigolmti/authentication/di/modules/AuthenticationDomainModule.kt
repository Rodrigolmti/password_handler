package br.com.rodrigolmti.authentication.di.modules

import br.com.rodrigolmti.authentication.data.repository.AppAuthenticationRepository
import br.com.rodrigolmti.authentication.domain.repository.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
internal abstract class AuthenticationDomainModule {

    @[Binds Reusable]
    abstract fun bindsAppDashboardRepository(
        appAuthenticationRepository: AppAuthenticationRepository
    ): AuthenticationRepository
}