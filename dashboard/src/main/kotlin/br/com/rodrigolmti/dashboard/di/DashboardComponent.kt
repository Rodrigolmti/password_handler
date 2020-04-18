package br.com.rodrigolmti.dashboard.di

import br.com.rodrigolmti.dashboard.ui.DashboardActivity
import br.com.rodrigolmti.dashboard.ui.passwords.PasswordsFragment
import br.com.rodrigolmti.dashboard.ui.password_generator.PasswordGeneratorFragment
import br.com.rodrigolmti.dashboard.ui.settings.SettingsFragment
import br.com.rodrigolmti.injector.CoreComponent
import br.com.rodrigolmti.injector.coreComponent
import dagger.Component

@DashboardScope
@Component(
    modules = [DashboardModule::class],
    dependencies = [CoreComponent::class]
)
interface DashboardComponent {

    fun inject(fragment: PasswordsFragment)

    fun inject(generatorFragment: PasswordGeneratorFragment)

    fun inject(fragment: SettingsFragment)

    @Component.Builder
    interface Builder {

        fun build(): DashboardComponent

        fun coreComponent(component: CoreComponent): Builder
    }

    companion object {

        fun inject(activity: DashboardActivity): DashboardComponent =
            DaggerDashboardComponent.builder()
                .coreComponent(activity.coreComponent())
                .build()
    }
}