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
import br.com.rodrigolmti.core_android.extensions.hideKeyboard
import br.com.rodrigolmti.core_android.navigation_modes.DefaultNavigationMode
import br.com.rodrigolmti.core_android.navigation_modes.NavigationMode
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.domain.model.PasswordModel
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.ui.DashboardActivity
import br.com.rodrigolmti.uikit.hide
import br.com.rodrigolmti.uikit.show
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.password_fragment.*

class PasswordFragment : BaseFragment(), NavigationMode by DefaultNavigationMode {

    private val viewModel by lazy { getViewModel(PasswordViewModel::class.java) }

    private val passwordModel: PasswordModel? by lazy {
        arguments?.let { PasswordFragmentArgs.fromBundle(it).passwordModel }
    }

    private val savedPasswordModel: SavedPasswordModel? by lazy {
        arguments?.let { PasswordFragmentArgs.fromBundle(it).savedPasswordModel }
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

        btnAction.setOnClickListener {
            content.hideKeyboard()
            viewModel.dispatchViewAction(
                PasswordAction.SavePassword(
                    id = savedPasswordModel?.id,
                    label = etPasswordLabel.text.toString(),
                    login = etPasswordLogin.text.toString(),
                    password = etPassword.text.toString(),
                    strength = colorIndicator.selectedPosition
                )
            )
        }

        etPassword.addTextChangedListener(TextChangedWatcher {
            viewModel.dispatchViewAction(PasswordAction.CalculatePasswordStrength(it))
        })

        passwordModel?.let { model ->
            toolbar.title = getString(R.string.password_fragment_title_save)
            btnAction.text = getString(R.string.action_save)
            etPassword.setText(model.password)
        }

        savedPasswordModel?.let { model ->
            toolbar.title = getString(R.string.password_fragment_title_edit)
            btnAction.text = getString(R.string.action_edit)
            etPasswordLabel.setText(model.label)
            etPasswordLogin.setText(model.login)
            etPassword.setText(model.password)
            toolbar.onMenuClick = {
                viewModel.dispatchViewAction(PasswordAction.DeleteSavedPassword(model))
            }
        }

        observeChanges()
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun observeChanges() {
        viewModel.viewState.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                PasswordViewState.State.IDLE -> {
                    toIdleState()
                }
                PasswordViewState.State.LOADING -> {
                    toLoadingState()
                }
            }.exhaustive
        })
        viewModel.viewState.action.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                is PasswordViewState.Action.ShowPasswordStrength -> {
                    colorIndicator.selectedPosition = action.strength
                }
                PasswordViewState.Action.ShowInvalidPasswordLabel -> {
                    Snackbar.make(
                        content,
                        getString(R.string.password_fragment_invalid_label), Snackbar.LENGTH_SHORT
                    ).show()
                }
                PasswordViewState.Action.ShowInvalidPassword -> {
                    Snackbar.make(
                        content,
                        getString(R.string.password_fragment_invalid_password),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                PasswordViewState.Action.ShowUpdatePasswordSuccess -> {
                }
                PasswordViewState.Action.ShowSavePasswordSuccess -> {
                }
                PasswordViewState.Action.ShowUpdatePasswordError -> {
                    Snackbar.make(
                        content,
                        getString(R.string.password_fragment_update_error),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                PasswordViewState.Action.ShowSavePasswordError -> {
                    Snackbar.make(
                        content,
                        getString(R.string.password_fragment_save_error),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                PasswordViewState.Action.ShowDeletePasswordSuccess -> {
                    findNavController().popBackStack()
                }
                PasswordViewState.Action.ShowDeletePasswordError -> {
                    Snackbar.make(
                        content,
                        getString(R.string.password_fragment_delete_error),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }.exhaustive
        })
    }

    private fun toIdleState() {
        btnAction.show()
        content.show()
        lottie.hide()
    }

    private fun toLoadingState() {
        btnAction.hide()
        content.hide()
        lottie.show()
    }
}