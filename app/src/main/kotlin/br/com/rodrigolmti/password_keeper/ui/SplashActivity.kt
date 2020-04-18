package br.com.rodrigolmti.password_keeper.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.rodrigolmti.password_keeper.R
import br.com.rodrigolmti.password_keeper.di.AppComponent

class SplashActivity : AppCompatActivity() {

    val component: AppComponent by lazy { AppComponent.inject() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
    }
}