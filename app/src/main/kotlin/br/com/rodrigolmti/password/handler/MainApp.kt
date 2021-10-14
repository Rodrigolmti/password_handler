package br.com.rodrigolmti.password.handler

import android.app.Application
import br.com.rodrigolmti.injector.CoreComponent
import br.com.rodrigolmti.injector.CoreComponentProvider

class MainApp : Application(), CoreComponentProvider {

    private lateinit var coreComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()
        coreComponent = CoreComponent.inject(this)
    }

    override fun coreComponent(): CoreComponent = coreComponent
}
