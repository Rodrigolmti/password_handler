package br.com.rodrigolmti.dashboard.ui.passwords

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.rodrigolmti.core_android.base.BaseFragment
import br.com.rodrigolmti.core_android.extensions.viewModelByFactory
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.ui.DashboardActivity
import br.com.rodrigolmti.dashboard.ui.password_generator.PasswordGeneratorViewModel
import br.com.rodrigolmti.dashboard.ui.settings.SettingsViewModel

class PasswordsFragment : BaseFragment() {

    private val viewModel by lazy { getViewModel(PasswordsViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.passwords_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as DashboardActivity).component.inject(this)
    }
}