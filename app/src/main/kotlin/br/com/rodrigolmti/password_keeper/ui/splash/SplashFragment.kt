package br.com.rodrigolmti.password_keeper.ui.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.rodrigolmti.core_android.base.BaseFragment
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.navigator.Actions
import br.com.rodrigolmti.password_keeper.R
import br.com.rodrigolmti.password_keeper.ui.MainActivity
import br.com.rodrigolmti.security.biometric.BiometricPromptManager
import br.com.rodrigolmti.security.domain.model.BiometricEvent
import br.com.rodrigolmti.uikit.hide
import br.com.rodrigolmti.uikit.show
import kotlinx.android.synthetic.main.fragment_splash.*
import javax.inject.Inject

private const val DELAY = 1000L

class SplashFragment : BaseFragment() {

    @Inject
    internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<SplashViewModel> { viewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

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
        tvTryAgain.setOnClickListener {
            toLoadingState()
            requestBiometric()
        }
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.viewState.action.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                is SplashViewState.Action.NavigateToDashboard -> {
                    startDashboardActivity()
                }
                is SplashViewState.Action.ValidateBiometric -> {
                    requestBiometric()
                }
            }.exhaustive
        })
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
        startActivity(Actions.openDashboard(requireContext()))
        activity?.finish()
    }

    private fun startAuthenticationActivity() {
        startActivity(Actions.openAuthentication(requireContext()))
        activity?.finish()
    }

    private fun toLoadingState() {
        lottie.show()
        imgVoid.hide()
        tvVoid.hide()
        tvTryAgain.hide()
    }

    private fun toErrorState() {
        lottie.hide()
        imgVoid.show()
        tvVoid.show()
        tvTryAgain.show()
    }
}