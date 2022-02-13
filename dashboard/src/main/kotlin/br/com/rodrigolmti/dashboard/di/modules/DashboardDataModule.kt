package br.com.rodrigolmti.dashboard.di.modules

import br.com.rodrigolmti.dashboard.data.data_source.AppDashboardLocalDataSource
import br.com.rodrigolmti.dashboard.data.data_source.DashboardLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
internal abstract class DashboardDataModule {

    @[Binds Reusable]
    abstract fun bindsAppLocalDataSource(
        appLocalDataSource: AppDashboardLocalDataSource
    ): DashboardLocalDataSource
}