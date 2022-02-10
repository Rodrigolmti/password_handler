package br.com.rodrigolmti.authentication.di.modules

import dagger.Module

@Module(
    includes = [
        AuthenticationDataModule::class,
        AuthenticationViewModelModule::class,
        AuthenticationDomainModule::class,
        AuthenticationDataModule::class,
    ]
)
internal class AuthenticationModule