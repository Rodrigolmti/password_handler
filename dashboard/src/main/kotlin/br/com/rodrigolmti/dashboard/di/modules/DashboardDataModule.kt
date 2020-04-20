package br.com.rodrigolmti.dashboard.di.modules

import br.com.rodrigolmti.dashboard.data.data_source.AppLocalDataSource
import br.com.rodrigolmti.dashboard.data.data_source.LocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
internal abstract class DashboardDataModule {

    @[Binds Reusable]
    abstract fun bindsAppLocalDataSource(
        appLocalDataSource: AppLocalDataSource
    ): LocalDataSource
}