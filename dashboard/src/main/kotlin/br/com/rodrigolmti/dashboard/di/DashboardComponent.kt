package br.com.rodrigolmti.dashboard.di

import br.com.rodrigolmti.dashboard.ui.DashboardActivity
import br.com.rodrigolmti.dashboard.ui.home.HomeFragment
import br.com.rodrigolmti.dashboard.ui.password.PasswordFragment
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

    fun inject(fragment: HomeFragment)

    fun inject(fragment: PasswordFragment)

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