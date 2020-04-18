package br.com.rodrigolmti.dashboard.ui.password_generator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.rodrigolmti.core_android.BaseFragment
import br.com.rodrigolmti.core_android.viewModelByFactory
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.ui.DashboardActivity
import br.com.rodrigolmti.dashboard.ui.settings.SettingsViewModel

class PasswordGeneratorFragment : BaseFragment() {

    private val viewModel by viewModelByFactory<SettingsViewModel> {
        SettingsViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.password_generator_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as DashboardActivity).component.inject(this)
    }
}