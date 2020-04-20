package br.com.rodrigolmti.injector

import android.app.Activity
import android.app.Application
import br.com.rodrigolmti.database.PasswordDatabase
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [CoreModule::class]
)
interface CoreComponent {

    fun providesDatabase(): PasswordDatabase

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): CoreComponent
    }

    companion object {

        fun inject(app: Application): CoreComponent = DaggerCoreComponent
            .builder()
            .application(app)
            .build()
    }
}

interface CoreComponentProvider {
    fun coreComponent(): CoreComponent
}

fun Activity.coreComponent() = (this.applicationContext as CoreComponentProvider).coreComponent()
