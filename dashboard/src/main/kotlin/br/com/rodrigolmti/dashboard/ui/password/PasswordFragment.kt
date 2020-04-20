package br.com.rodrigolmti.dashboard.ui.password

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.rodrigolmti.core_android.base.BaseFragment
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.ui.DashboardActivity
import br.com.rodrigolmti.core_android.navigation_modes.DefaultNavigationMode
import br.com.rodrigolmti.core_android.navigation_modes.NavigationMode
import kotlinx.android.synthetic.main.password_fragment.*

class PasswordFragment : BaseFragment(), NavigationMode by DefaultNavigationMode {

    private val viewModel by lazy { getViewModel(PasswordViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.password_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as DashboardActivity).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFields()
    }

    private fun setupFields() {

    }
}