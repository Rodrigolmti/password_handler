package br.com.rodrigolmti.dashboard.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import br.com.rodrigolmti.core_android.base.BaseActivity
import br.com.rodrigolmti.core_android.navigation_modes.NavigationContainer
import br.com.rodrigolmti.core_android.navigation_modes.NavigationMode
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.di.DashboardComponent
import br.com.rodrigolmti.uikit.hide
import br.com.rodrigolmti.uikit.show
import kotlinx.android.synthetic.main.dashboard_activity.*

class DashboardActivity : BaseActivity(), NavigationContainer {

    val component: DashboardComponent by lazy { DashboardComponent.inject(this) }

    private val navHostFragment: NavHostFragment? by lazy {
        supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)
        setupFields()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    private fun setupFields() {
        navHostFragment?.let {
            NavigationUI.setupWithNavController(
                bottomNavigation,
                it.navController
            )
        }
    }

    override fun onBackPressed() {
        navHostFragment?.let { navHost ->
            val currentFragment = navHost.childFragmentManager.fragments[0]
            if (currentFragment is NavigationMode && !currentFragment.isImmersive()) {
                navHost.navController.popBackStack()
            } else {
                super.onBackPressed()
            }
        } ?: run {
            super.onBackPressed()
        }
    }

    override var isImmersiveFlow: Boolean = true
        set(value) {
            if (value) bottomNavigation.show() else bottomNavigation.hide()
            field = value
        }
}