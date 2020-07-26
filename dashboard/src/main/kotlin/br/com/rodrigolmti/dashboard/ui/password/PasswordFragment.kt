package br.com.rodrigolmti.dashboard.ui.password

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
import kotlinx.android.synthetic.main.password_fragment.*
import javax.inject.Inject

class PasswordFragment : BaseFragment(), NavigationMode by DefaultNavigationMode {

    @Inject
    internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<PasswordViewModel> { viewModelProviderFactory }

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

        imgShuffle.setOnClickListener {
            viewModel.dispatchViewAction(PasswordAction.ShufflePassword)
        }

        toolbar.title = getString(R.string.password_fragment_title_save)

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
                is PasswordViewState.Action.ShowInvalidPasswordLabel -> {
                    showSnackBar(getString(R.string.password_fragment_invalid_label))
                }
                is PasswordViewState.Action.ShowInvalidPassword -> {
                    showSnackBar(getString(R.string.password_fragment_invalid_password))
                }
                is PasswordViewState.Action.ShowUpdatePasswordSuccess -> {
                    showSnackBar(getString(R.string.password_fragment_password_updated))
                }
                is PasswordViewState.Action.ShowSavePasswordSuccess -> {
                    showSnackBar(getString(R.string.password_fragment_password_saved))
                }
                is PasswordViewState.Action.ShowUpdatePasswordError -> {
                    showSnackBar(getString(R.string.password_fragment_update_error))
                }
                is PasswordViewState.Action.ShowSavePasswordError -> {
                    showSnackBar(getString(R.string.password_fragment_save_error))
                }
                is PasswordViewState.Action.ShowDeletePasswordSuccess -> {
                    findNavController().popBackStack()
                }
                is PasswordViewState.Action.ShowDeletePasswordError -> {
                    showSnackBar(getString(R.string.password_fragment_delete_error))
                }
                is PasswordViewState.Action.ShowGeneratedPassword -> {
                    etPassword.setText(action.password)
                }
                is PasswordViewState.Action.ShowGeneratePasswordError -> {
                    showSnackBar(getString(R.string.password_fragment_generate_password_error))
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