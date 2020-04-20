package br.com.rodrigolmti.dashboard.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import br.com.rodrigolmti.core_android.navigation_modes.NavigationContainer
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.di.DashboardComponent
import br.com.rodrigolmti.uikit.hide
import br.com.rodrigolmti.uikit.show
import kotlinx.android.synthetic.main.dashboard_activity.*

class DashboardActivity : AppCompatActivity(), NavigationContainer {

    val component: DashboardComponent by lazy { DashboardComponent.inject(this) }

    private val navHostFragment: NavHostFragment? by lazy {
        supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)
        setupFields()
    }

    private fun setupFields() {
        navHostFragment?.let {
            NavigationUI.setupWithNavController(
                bottomNavigation,
                it.navController
            )
        }
    }

    override var isImmersiveFlow: Boolean = true
        set(value) {
            if (value) bottomNavigation.show() else bottomNavigation.hide()
            field = value
        }
}