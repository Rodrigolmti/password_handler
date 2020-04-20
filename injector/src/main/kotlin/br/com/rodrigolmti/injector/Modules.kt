package br.com.rodrigolmti.injector

import androidx.lifecycle.ViewModelProvider
import br.com.rodrigolmti.database.AppPasswordDatabase
import br.com.rodrigolmti.database.PasswordDatabase
import br.com.rodrigolmti.injector.common.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
internal abstract class CoreModule {

    @[Binds]
    abstract fun bindAppDatabase(
        appPasswordDatabase: AppPasswordDatabase
    ): PasswordDatabase
}

@Module
abstract class ViewModelFactoryModule {

    @[Binds]
    abstract fun bindViewModelFactory(
        factory: ViewModelProviderFactory
    ): ViewModelProvider.Factory
}