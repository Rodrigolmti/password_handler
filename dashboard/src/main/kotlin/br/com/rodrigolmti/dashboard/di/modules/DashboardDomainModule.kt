package br.com.rodrigolmti.dashboard.di.modules

import br.com.rodrigolmti.dashboard.data.repository.AppDashboardRepository
import br.com.rodrigolmti.dashboard.domain.repository.DashboardRepository
import br.com.rodrigolmti.dashboard.domain.use_cases.*
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
internal abstract class DashboardDomainModule {

    @[Binds Reusable]
    abstract fun bindsAppDashboardRepository(
        appDashboardRepository: AppDashboardRepository
    ): DashboardRepository

    @[Binds Reusable]
    abstract fun bindsGetAllSavedPasswordsUseCase(
        getAllSavedPasswords: GetAllSavedPasswords
    ): GetAllSavedPasswordsUseCase

    @[Binds Reusable]
    abstract fun bindsSavePasswordUseCase(
        savePassword: SavePassword
    ): SavePasswordUseCase

    @[Binds Reusable]
    abstract fun bindsGeneratePasswordUseCase(
        generatePassword: GeneratePassword
    ): GeneratePasswordUseCase

    @[Binds Reusable]
    abstract fun bindsPasswordStrengthUseCase(
        passwordStrength: PasswordStrength
    ): PasswordStrengthUseCase
}