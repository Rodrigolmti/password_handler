package br.com.rodrigolmti.dashboard.di.modules

import dagger.Module

@Module(includes = [DashboardDomainModule::class, DashboardViewModelModule::class])
internal class DashboardModule