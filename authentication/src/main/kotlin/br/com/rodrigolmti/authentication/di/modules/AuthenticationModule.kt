package br.com.rodrigolmti.authentication.di.modules

import dagger.Module

@Module(
    includes = [AuthenticationDataModule::class,
        AuthenticationViewModelModule::class,
        AuthenticationDataModule::class]
)
internal class AuthenticationModule