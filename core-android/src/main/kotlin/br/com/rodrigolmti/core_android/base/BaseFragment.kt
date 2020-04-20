package br.com.rodrigolmti.core_android.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.rodrigolmti.core_android.navigation_modes.NavigationContainer
import br.com.rodrigolmti.core_android.navigation_modes.NavigationMode
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    fun <VM : ViewModel> getViewModel(modelClass: Class<VM>): VM =
        ViewModelProvider(this, viewModelFactory).get(modelClass)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavigationVisibility(view)
    }

    private fun setupBottomNavigationVisibility(view: View) {
        setNavigationVisible(parentFragmentManager
            .fragments
            .mapNotNull { it as? NavigationMode }
            .lastOrNull()
            ?.isImmersive() ?: false)
    }

    private fun setNavigationVisible(isImmersiveFlow: Boolean) {
        (activity as? NavigationContainer)?.isImmersiveFlow = isImmersiveFlow
    }
}