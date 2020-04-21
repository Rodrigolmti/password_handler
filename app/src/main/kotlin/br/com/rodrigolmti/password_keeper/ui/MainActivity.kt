package br.com.rodrigolmti.password_keeper.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.rodrigolmti.password_keeper.R
import br.com.rodrigolmti.password_keeper.di.AppComponent

class MainActivity : AppCompatActivity() {

    val component: AppComponent by lazy { AppComponent.inject(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
    }
}