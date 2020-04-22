package br.com.rodrigolmti.dashboard.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.whenCreated
import br.com.rodrigolmti.core_android.base.BaseFragment
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.core_android.extensions.viewModelByFactory
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.ui.DashboardActivity
import br.com.rodrigolmti.core_android.navigation_modes.ImmersiveNavigationMode
import br.com.rodrigolmti.core_android.navigation_modes.NavigationMode
import kotlinx.android.synthetic.main.settings_fragment.*

class SettingsFragment : BaseFragment(), NavigationMode by ImmersiveNavigationMode {

    private val viewModel by lazy { getViewModel(SettingsViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as DashboardActivity).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dispatchViewAction(SettingsAction.Init)
        setupFields()
    }

    private fun setupFields() {
        sBiometric.setOnCheckedChangeListener { _, isChecked ->
            viewModel.dispatchViewAction(SettingsAction.BiometricChanged(isChecked))
        }
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.viewState.action.observe(viewLifecycleOwner, Observer { action ->
            when(action) {
                is SettingsViewState.Action.UpdateBiometricSwitch -> {
                    sBiometric.isChecked = action.checked
                }
            }.exhaustive
        })
    }
}