package br.com.rodrigolmti.dashboard.di.modules

import dagger.Module

@Module(
    includes = [DashboardDomainModule::class,
        DashboardViewModelModule::class,
        DashboardDataModule::class]
)
internal class DashboardModule