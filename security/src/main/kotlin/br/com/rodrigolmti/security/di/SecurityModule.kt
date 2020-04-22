package br.com.rodrigolmti.security.di

import br.com.rodrigolmti.security.data.data_source.AppKeyLocalDataSource
import br.com.rodrigolmti.security.data.data_source.KeyLocalDataSource
import br.com.rodrigolmti.security.data.repository.AppSecurityRepository
import br.com.rodrigolmti.security.domain.repository.SecurityRepository
import br.com.rodrigolmti.security.domain.use_case.*
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module(includes = [SecurityDomainModule::class, SecurityDataModule::class])
abstract class SecurityModule

@Module
abstract class SecurityDomainModule {

    @[Binds Reusable]
    abstract fun bindsKeyRepository(
        appKeyRepository: AppSecurityRepository
    ): SecurityRepository

    @[Binds Reusable]
    abstract fun bindsGenerateKeyUseCase(
        generateKey: GenerateSecretKey
    ): GenerateSecretKeyUseCase

    @[Binds Reusable]
    abstract fun bindsGenerateVectorUseCase(
        generateVector: GenerateVector
    ): GenerateVectorUseCase

    @[Binds Reusable]
    abstract fun bindsGenerateKeysUseCase(
        generateKeys: GenerateKeys
    ): GenerateKeysUseCase

    @[Binds Reusable]
    abstract fun bindsEncodePasswordUseCase(
        encodePassword: EncodePassword
    ): EncodePasswordUseCase

    @[Binds Reusable]
    abstract fun bindsDecodePasswordUseCase(
        decodePassword: DecodePassword
    ): DecodePasswordUseCase

    @[Binds Reusable]
    abstract fun bindsUpdateUserBiometricUseCase(
        updateUserBiometric: UpdateUserBiometric
    ): UpdateUserBiometricUseCase

    @[Binds Reusable]
    abstract fun bindsGetUserBiometricUseCase(
        getUserBiometric: GetUserBiometric
    ): GetUserBiometricUseCase
}

@Module
internal abstract class SecurityDataModule {

    @[Binds Reusable]
    abstract fun bindsKeyLocalDataSource(
        appKeyLocalDataSource: AppKeyLocalDataSource
    ): KeyLocalDataSource
}