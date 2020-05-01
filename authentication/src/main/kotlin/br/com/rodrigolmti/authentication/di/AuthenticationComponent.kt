package br.com.rodrigolmti.authentication.di

import br.com.rodrigolmti.authentication.di.modules.AuthenticationModule
import br.com.rodrigolmti.authentication.ui.AuthenticationActivity
import br.com.rodrigolmti.authentication.ui.pin.PinFragment
import br.com.rodrigolmti.injector.CoreComponent
import br.com.rodrigolmti.injector.ViewModelFactoryModule
import br.com.rodrigolmti.injector.coreComponent
import br.com.rodrigolmti.security.di.SecurityModule
import dagger.Component

@AuthenticationScope
@Component(
    modules = [ViewModelFactoryModule::class, AuthenticationModule::class, SecurityModule::class],
    dependencies = [CoreComponent::class]
)
interface AuthenticationComponent {

    fun inject(fragment: PinFragment)

    @Component.Builder
    interface Builder {

        fun coreComponent(component: CoreComponent): Builder

        fun build(): AuthenticationComponent
    }

    companion object {

        fun inject(activity: AuthenticationActivity): AuthenticationComponent =
            DaggerAuthenticationComponent.builder()
                .coreComponent(activity.coreComponent())
                .build()
    }
}