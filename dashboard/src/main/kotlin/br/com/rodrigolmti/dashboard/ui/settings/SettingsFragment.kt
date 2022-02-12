package br.com.rodrigolmti.dashboard.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import br.com.rodrigolmti.core_android.base.BaseFragment
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.core_android.navigation_modes.ImmersiveNavigationMode
import br.com.rodrigolmti.core_android.navigation_modes.NavigationMode
import br.com.rodrigolmti.core_android.view_binding_delegate.viewBinding
import br.com.rodrigolmti.dashboard.databinding.SettingsFragmentBinding
import br.com.rodrigolmti.dashboard.ui.DashboardActivity
import br.com.rodrigolmti.navigator.AuthenticationOrigin
import br.com.rodrigolmti.navigator.Navigator
import javax.inject.Inject

class SettingsFragment : BaseFragment(), NavigationMode by ImmersiveNavigationMode {

    @Inject
    internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val binding by viewBinding {
        SettingsFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<SettingsViewModel> { viewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

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
        binding.sBiometric.setOnCheckedChangeListener { _, isChecked ->
//            viewModel.dispatchViewAction(SettingsAction.BiometricChanged(isChecked))
            startAuthenticationActivity()
        }
        observeChanges()
    }

    private fun startAuthenticationActivity() {
        Navigator.navigateToAuthentication(requireContext(), AuthenticationOrigin.SETTINGS)
    }

    private fun observeChanges() {
        viewModel.viewState.action.observe(viewLifecycleOwner) { action ->
            when (action) {
                is SettingsViewState.Action.UpdateBiometricSwitch -> {
                    binding.sBiometric.isChecked = action.checked
                }
            }.exhaustive
        }
    }
}