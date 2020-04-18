package br.com.rodrigolmti.injector

import android.app.Activity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [CoreModule::class]
)
interface CoreComponent {

    companion object {

        fun inject(): CoreComponent = DaggerCoreComponent.builder().build()
    }
}

interface CoreComponentProvider {
    fun coreComponent(): CoreComponent
}

fun Activity.coreComponent() = (this.applicationContext as CoreComponentProvider).coreComponent()
