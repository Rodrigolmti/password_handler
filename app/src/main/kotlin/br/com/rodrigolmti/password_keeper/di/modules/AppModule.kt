package br.com.rodrigolmti.password_keeper.di.modules

import androidx.lifecycle.ViewModel
import br.com.rodrigolmti.injector.common.ViewModelKey
import br.com.rodrigolmti.password_keeper.data.data_source.AppKeyLocalDataSource
import br.com.rodrigolmti.password_keeper.data.data_source.KeyLocalDataSource
import br.com.rodrigolmti.password_keeper.data.repository.AppKeyRepository
import br.com.rodrigolmti.password_keeper.domain.repository.KeyRepository
import br.com.rodrigolmti.password_keeper.domain.use_case.GenerateKey
import br.com.rodrigolmti.password_keeper.domain.use_case.GenerateKeyUseCase
import br.com.rodrigolmti.password_keeper.domain.use_case.GenerateVector
import br.com.rodrigolmti.password_keeper.domain.use_case.GenerateVectorUseCase
import br.com.rodrigolmti.password_keeper.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.multibindings.IntoMap

@Module(includes = [AppDomainModule::class, AppDataModule::class, AppViewModelModule::class])
abstract class AppModule

@Module
internal abstract class AppDomainModule {

    @[Binds Reusable]
    abstract fun bindsKeyRepository(
        appKeyRepository: AppKeyRepository
    ): KeyRepository

    @[Binds Reusable]
    abstract fun bindsGenerateKeyUseCase(
        generateKey: GenerateKey
    ): GenerateKeyUseCase

    @[Binds Reusable]
    abstract fun bindsGenerateVectorUseCase(
        generateVector: GenerateVector
    ): GenerateVectorUseCase
}

@Module
internal abstract class AppDataModule {

    @[Binds Reusable]
    abstract fun bindsKeyLocalDataSource(
        appKeyLocalDataSource: AppKeyLocalDataSource
    ): KeyLocalDataSource
}

@Module
internal abstract class AppViewModelModule {

    @[Binds IntoMap ViewModelKey(SplashViewModel::class)]
    internal abstract fun bindSplashViewModel(
        viewModel: SplashViewModel
    ): ViewModel
}


