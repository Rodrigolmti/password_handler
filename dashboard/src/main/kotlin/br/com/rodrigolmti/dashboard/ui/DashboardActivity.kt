package br.com.rodrigolmti.dashboard.ui

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import br.com.rodrigolmti.core_android.base.BaseActivity
import br.com.rodrigolmti.core_android.navigation_modes.NavigationContainer
import br.com.rodrigolmti.core_android.navigation_modes.NavigationMode
import br.com.rodrigolmti.core_android.view_binding_delegate.viewBinding
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.databinding.DashboardActivityBinding
import br.com.rodrigolmti.dashboard.di.DashboardComponent
import br.com.rodrigolmti.uikit.hide
import br.com.rodrigolmti.uikit.show

class DashboardActivity : BaseActivity(), NavigationContainer {

    private val binding by viewBinding {
        DashboardActivityBinding.inflate(layoutInflater)
    }

    val component: DashboardComponent by lazy { DashboardComponent.inject(this) }

    private val navHostFragment: NavHostFragment? by lazy {
        supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupFields()
    }

    private fun setupFields() {
        navHostFragment?.let {
            NavigationUI.setupWithNavController(
                binding.bottomNavigation,
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
            if (value) binding.bottomNavigation.show() else binding.bottomNavigation.hide()
            field = value
        }
}