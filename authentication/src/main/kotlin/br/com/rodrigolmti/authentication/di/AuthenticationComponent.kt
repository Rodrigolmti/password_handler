package br.com.rodrigolmti.authentication.di

import br.com.rodrigolmti.authentication.di.modules.AuthenticationModule
import br.com.rodrigolmti.authentication.ui.AuthenticationActivity
import br.com.rodrigolmti.injector.CoreComponent
import br.com.rodrigolmti.injector.ViewModelFactoryModule
import br.com.rodrigolmti.injector.coreComponent
import dagger.Component

@AuthenticationScope
@Component(
    modules = [ViewModelFactoryModule::class, AuthenticationModule::class],
    dependencies = [CoreComponent::class]
)
interface AuthenticationComponent {

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