package br.com.rodrigolmti.authentication.di.modules

import dagger.Module

@Module(
    includes = [
        AuthenticationDataModule::class,
        AuthenticationViewModelModule::class,
        AuthenticationDomainModule::class,
    ]
)
internal class AuthenticationModule