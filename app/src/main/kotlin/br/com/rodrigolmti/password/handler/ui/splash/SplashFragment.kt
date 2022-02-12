package br.com.rodrigolmti.password.handler.ui.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import br.com.rodrigolmti.core_android.base.BaseFragment
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.core_android.view_binding_delegate.viewBinding
import br.com.rodrigolmti.navigator.AuthenticationOrigin
import br.com.rodrigolmti.navigator.Navigator
import br.com.rodrigolmti.password.handler.R
import br.com.rodrigolmti.password.handler.databinding.FragmentSplashBinding
import br.com.rodrigolmti.password.handler.ui.MainActivity
import br.com.rodrigolmti.security.biometric.BiometricPromptManager
import br.com.rodrigolmti.security.domain.model.BiometricEvent
import br.com.rodrigolmti.uikit.hide
import br.com.rodrigolmti.uikit.show
import javax.inject.Inject

private const val DELAY = 1000L

class SplashFragment : BaseFragment() {

    @Inject
    internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val binding by viewBinding {
        FragmentSplashBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<SplashViewModel> { viewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as MainActivity).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            viewModel.dispatchViewAction(SplashAction.Init)
        }, DELAY)
        setupFields()
    }

    private fun setupFields() {
        binding.tvTryAgain.setOnClickListener {
            toLoadingState()
            requestBiometric()
        }
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.viewState.action.observe(viewLifecycleOwner) { action ->
            when (action) {
                is SplashViewState.Action.NavigateToDashboard -> {
                    startDashboardActivity()
                }
                is SplashViewState.Action.ValidateBiometric -> {
                    requestBiometric()
                }
            }.exhaustive
        }
    }

    private fun requestBiometric() {
        BiometricPromptManager(
            context,
            childFragmentManager,
            title = getString(R.string.splash_fragment_biometric_title),
            subtitle = getString(R.string.splash_fragment_biometric_subtitle)
        ) authenticate { biometricEvent ->
            when (biometricEvent) {
                BiometricEvent.AuthenticationFailed -> {
                    toErrorState()
                }
                BiometricEvent.AuthenticationCancelled -> {
                    toErrorState()
                }
                BiometricEvent.AuthenticationSucceeded -> {
                    startDashboardActivity()
                }
            }
        }
    }

    private fun startDashboardActivity() {
        Navigator.navigateToDashboard(requireContext())
        activity?.finish()
    }

    private fun startAuthenticationActivity() {
        Navigator.navigateToAuthentication(requireContext(), AuthenticationOrigin.AUTHENTICATION)
        activity?.finish()
    }

    private fun toLoadingState() {
        binding.lottie.show()
        binding.imgVoid.hide()
        binding.tvVoid.hide()
        binding.tvTryAgain.hide()
    }

    private fun toErrorState() {
        binding.lottie.hide()
        binding.imgVoid.show()
        binding.tvVoid.show()
        binding.tvTryAgain.show()
    }
}