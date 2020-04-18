package br.com.rodrigolmti.password_keeper.di

import br.com.rodrigolmti.password_keeper.di.modules.AppModule
import br.com.rodrigolmti.password_keeper.ui.SplashFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent {

    fun inject(fragment: SplashFragment)

    companion object {

        fun inject(): AppComponent = DaggerAppComponent.builder().build()
    }
}