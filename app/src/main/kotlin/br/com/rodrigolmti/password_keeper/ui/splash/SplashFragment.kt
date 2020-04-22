package br.com.rodrigolmti.password_keeper.ui.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import br.com.rodrigolmti.core_android.base.BaseFragment
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.dashboard.domain.model.BiometricEvent
import br.com.rodrigolmti.dashboard.ui.biometric.BiometricPromptManager
import br.com.rodrigolmti.navigator.Actions
import br.com.rodrigolmti.password_keeper.R
import br.com.rodrigolmti.password_keeper.ui.MainActivity

private const val DELAY = 1000L

class SplashFragment : BaseFragment() {

    private val viewModel by lazy { getViewModel(SplashViewModel::class.java) }

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
//        Handler().postDelayed({
//            viewModel.dispatchViewAction(SplashAction.Init)
//        }, DELAY)

        BiometricPromptManager(
            context,
            childFragmentManager,
            title = "Authenticate",
            negativeButtonText = "Cancel"
        ) authenticate { biometricEvent ->
            when (biometricEvent) {
                BiometricEvent.AuthenticationCancelled -> {
                    print("")
                }
                BiometricEvent.AuthenticationSucceeded -> {
                    print("")
                }
            }
        }


        setupFields()
    }

    private fun setupFields() {
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.viewState.action.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                is SplashViewState.Action.KeyGenerationSucceeded -> {
                    startActivity(Actions.openDashboard(requireContext()))
                    activity?.finish()
                }
                is SplashViewState.Action.KeyGenerationError -> {
                    startActivity(Actions.openDashboard(requireContext()))
                    activity?.finish()
                    //TODO: Handle the error, log events and bla bla
                }
            }.exhaustive
        })
    }
}