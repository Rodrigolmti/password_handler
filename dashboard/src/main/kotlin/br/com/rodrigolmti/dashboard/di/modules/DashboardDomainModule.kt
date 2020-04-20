package br.com.rodrigolmti.dashboard.di.modules

import br.com.rodrigolmti.dashboard.domain.use_cases.GeneratePassword
import br.com.rodrigolmti.dashboard.domain.use_cases.GeneratePasswordUseCase
import br.com.rodrigolmti.dashboard.domain.use_cases.PasswordStrength
import br.com.rodrigolmti.dashboard.domain.use_cases.PasswordStrengthUseCase
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
internal abstract class DashboardDomainModule {

    @[Binds Reusable]
    abstract fun bindsGeneratePasswordUseCase(
        generatePassword: GeneratePassword
    ): GeneratePasswordUseCase

    @[Binds Reusable]
    abstract fun bindsPasswordStrengthUseCase(
        passwordStrength: PasswordStrength
    ): PasswordStrengthUseCase
}