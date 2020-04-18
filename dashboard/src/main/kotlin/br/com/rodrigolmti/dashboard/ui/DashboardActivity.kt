package br.com.rodrigolmti.dashboard.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.di.DashboardComponent
import kotlinx.android.synthetic.main.dashboard_activity.*


class DashboardActivity : AppCompatActivity() {

    val component: DashboardComponent by lazy { DashboardComponent.inject(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)
        setupFields()
    }

    private fun setupFields() {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)?.let {
            NavigationUI.setupWithNavController(
                bottom_navigation,
                it.navController
            )
        }
    }
}