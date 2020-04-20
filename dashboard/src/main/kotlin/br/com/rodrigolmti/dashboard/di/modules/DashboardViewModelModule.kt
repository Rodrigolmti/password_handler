package br.com.rodrigolmti.dashboard.di.modules

import androidx.lifecycle.ViewModel
import br.com.rodrigolmti.dashboard.ui.password_generator.PasswordGeneratorViewModel
import br.com.rodrigolmti.dashboard.ui.password.PasswordViewModel
import br.com.rodrigolmti.dashboard.ui.settings.SettingsViewModel
import br.com.rodrigolmti.injector.common.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class DashboardViewModelModule {

    @[Binds IntoMap ViewModelKey(PasswordGeneratorViewModel::class)]
    internal abstract fun bindPasswordGeneratorViewModel(
        viewModel: PasswordGeneratorViewModel
    ): ViewModel

    @[Binds IntoMap ViewModelKey(PasswordViewModel::class)]
    internal abstract fun bindPasswordsViewModel(
        viewModel: PasswordViewModel
    ): ViewModel

    @[Binds IntoMap ViewModelKey(SettingsViewModel::class)]
    internal abstract fun bindSettingsViewModel(
        viewModel: SettingsViewModel
    ): ViewModel
}