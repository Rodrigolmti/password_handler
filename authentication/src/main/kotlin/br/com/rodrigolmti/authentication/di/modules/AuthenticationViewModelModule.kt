package br.com.rodrigolmti.authentication.di.modules

import androidx.lifecycle.ViewModel
import br.com.rodrigolmti.authentication.ui.pin.PinViewModel
import br.com.rodrigolmti.injector.common.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class AuthenticationViewModelModule {

    @[Binds IntoMap ViewModelKey(PinViewModel::class)]
    internal abstract fun bindPinViewModel(
        viewModel: PinViewModel
    ): ViewModel
}