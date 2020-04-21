package br.com.rodrigolmti.password_keeper.ui.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.rodrigolmti.core_android.base.BaseFragment
import br.com.rodrigolmti.navigator.Actions
import br.com.rodrigolmti.password_keeper.R
import br.com.rodrigolmti.password_keeper.ui.MainActivity

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            startActivity(Actions.openDashboard(requireContext()))
            activity?.finish()
        }, 2000L)
    }
}