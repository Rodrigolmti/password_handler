package br.com.rodrigolmti.password_keeper.di.modules

import androidx.lifecycle.ViewModel
import br.com.rodrigolmti.injector.common.ViewModelKey
import br.com.rodrigolmti.password_keeper.ui.splash.SplashViewModel
import br.com.rodrigolmti.security.di.SecurityModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [AppViewModelModule::class, SecurityModule::class])
abstract class AppModule

@Module
internal abstract class AppViewModelModule {

    @[Binds IntoMap ViewModelKey(SplashViewModel::class)]
    internal abstract fun bindSplashViewModel(
        viewModel: SplashViewModel
    ): ViewModel
}


