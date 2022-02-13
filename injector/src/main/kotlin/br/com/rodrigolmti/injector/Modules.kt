package br.com.rodrigolmti.injector

import androidx.lifecycle.ViewModelProvider
import br.com.rodrigolmti.database.AppPasswordDatabase
import br.com.rodrigolmti.database.PasswordDatabase
import br.com.rodrigolmti.injector.common.ViewModelProviderFactory
import br.com.rodrigolmti.user_preferences.AppUserPreferences
import br.com.rodrigolmti.user_preferences.UserPreferences
import dagger.Binds
import dagger.Module

@Module
internal abstract class CoreModule {

    @[Binds]
    abstract fun bindUserPreferences(
        appUserPreferences: AppUserPreferences
    ): UserPreferences

    @[Binds]
    abstract fun bindAppDatabase(
        appPasswordDatabase: AppPasswordDatabase
    ): PasswordDatabase

    @[Binds]
    abstract fun bindDispatcherProvider(
        appDispatcherProvider: AppDispatcherProvider
    ): DispatcherProvider
}

@Module
abstract class ViewModelFactoryModule {

    @[Binds]
    abstract fun bindViewModelFactory(
        factory: ViewModelProviderFactory
    ): ViewModelProvider.Factory
}