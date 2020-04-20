package br.com.rodrigolmti.dashboard.ui.password

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.rodrigolmti.core_android.TextChangedWatcher
import br.com.rodrigolmti.core_android.base.BaseFragment
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.core_android.navigation_modes.DefaultNavigationMode
import br.com.rodrigolmti.core_android.navigation_modes.NavigationMode
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.domain.model.PasswordModel
import br.com.rodrigolmti.dashboard.ui.DashboardActivity
import kotlinx.android.synthetic.main.password_fragment.*

class PasswordFragment : BaseFragment(), NavigationMode by DefaultNavigationMode {

    private val viewModel by lazy { getViewModel(PasswordViewModel::class.java) }

    private val passwordModel: PasswordModel? by lazy {
        arguments?.let { PasswordFragmentArgs.fromBundle(it).passwordModel }
    }

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
        toolbar.onBackClick = {
            findNavController().popBackStack()
        }

        etPassword.addTextChangedListener(TextChangedWatcher {
            viewModel.dispatchViewAction(PasswordAction.CalculatePasswordStrength(it))
        })

        passwordModel?.let { model ->
            toolbar.title = getString(R.string.password_fragment_title_save)
            btnAction.text = getString(R.string.action_save)
            etPassword.setText(model.password)
        }
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.viewState.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                PasswordViewState.State.IDLE -> {
                }
                PasswordViewState.State.LOADING -> {
                }
            }.exhaustive
        })
        viewModel.viewState.action.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                is PasswordViewState.Action.ShowPasswordStrength -> {
                    colorIndicator.selectedPosition = action.strength
                }
            }.exhaustive
        })
    }
}