package br.com.rodrigolmti.password.handler.di

import br.com.rodrigolmti.injector.CoreComponent
import br.com.rodrigolmti.injector.ViewModelFactoryModule
import br.com.rodrigolmti.injector.coreComponent
import br.com.rodrigolmti.password.handler.di.modules.AppModule
import br.com.rodrigolmti.password.handler.ui.MainActivity
import br.com.rodrigolmti.password.handler.ui.splash.SplashFragment
import dagger.Component

@AppScope
@Component(
    modules = [ViewModelFactoryModule::class, AppModule::class],
    dependencies = [CoreComponent::class]
)
interface AppComponent {

    fun inject(fragment: SplashFragment)

    @Component.Builder
    interface Builder {

        fun coreComponent(component: CoreComponent): Builder

        fun build(): AppComponent
    }

    companion object {

        fun inject(activity: MainActivity): AppComponent = DaggerAppComponent.builder()
            .coreComponent(activity.coreComponent())
            .build()
    }
}