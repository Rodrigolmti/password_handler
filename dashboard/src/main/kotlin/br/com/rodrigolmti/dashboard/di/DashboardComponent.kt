package br.com.rodrigolmti.dashboard.di

import br.com.rodrigolmti.dashboard.di.modules.DashboardModule
import br.com.rodrigolmti.dashboard.ui.DashboardActivity
import br.com.rodrigolmti.dashboard.ui.password.PasswordFragment
import br.com.rodrigolmti.dashboard.ui.password_generator.PasswordGeneratorFragment
import br.com.rodrigolmti.dashboard.ui.passwords.PasswordsFragment
import br.com.rodrigolmti.dashboard.ui.settings.SettingsFragment
import br.com.rodrigolmti.injector.CoreComponent
import br.com.rodrigolmti.injector.ViewModelFactoryModule
import br.com.rodrigolmti.injector.coreComponent
import br.com.rodrigolmti.security.di.SecurityModule
import dagger.Component

@DashboardScope
@Component(
    modules = [ViewModelFactoryModule::class, DashboardModule::class, SecurityModule::class],
    dependencies = [CoreComponent::class]
)
interface DashboardComponent {

    fun inject(fragment: PasswordFragment)

    fun inject(fragment: PasswordsFragment)

    fun inject(generatorFragment: PasswordGeneratorFragment)

    fun inject(fragment: SettingsFragment)

    fun inject(fragment: DashboardActivity)

    @Component.Builder
    interface Builder {

        fun coreComponent(component: CoreComponent): Builder

        fun build(): DashboardComponent
    }

    companion object {

        fun create(activity: DashboardActivity): DashboardComponent =
            DaggerDashboardComponent.builder()
                .coreComponent(activity.coreComponent())
                .build()
    }
}