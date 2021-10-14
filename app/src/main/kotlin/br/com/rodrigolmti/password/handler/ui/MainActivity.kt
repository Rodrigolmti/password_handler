package br.com.rodrigolmti.password.handler.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.rodrigolmti.password.handler.R
import br.com.rodrigolmti.password.handler.di.AppComponent

class MainActivity : AppCompatActivity() {

    val component: AppComponent by lazy { AppComponent.inject(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
    }
}