package br.com.rodrigolmti.injector

import androidx.lifecycle.ViewModelProvider
import br.com.rodrigolmti.injector.common.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
internal class CoreModule

@Module
abstract class ViewModelFactoryModule {

    @[Binds]
    abstract fun bindViewModelFactory(
        factory: ViewModelProviderFactory
    ): ViewModelProvider.Factory
}