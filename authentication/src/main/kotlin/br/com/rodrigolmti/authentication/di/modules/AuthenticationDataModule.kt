package br.com.rodrigolmti.authentication.di.modules

import br.com.rodrigolmti.authentication.data.data_source.AppLocalDataSource
import br.com.rodrigolmti.authentication.data.data_source.LocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
internal abstract class AuthenticationDataModule {

    @[Binds Reusable]
    abstract fun bindsAppLocalDataSource(
        appLocalDataSource: AppLocalDataSource
    ): LocalDataSource
}