package br.com.rodrigolmti.dashboard.ui.password

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.rodrigolmti.core_android.TextChangedWatcher
import br.com.rodrigolmti.core_android.base.BaseFragment
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.core_android.extensions.hideKeyboard
import br.com.rodrigolmti.core_android.navigation_modes.DefaultNavigationMode
import br.com.rodrigolmti.core_android.navigation_modes.NavigationMode
import br.com.rodrigolmti.core_android.view_binding_delegate.viewBinding
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.databinding.PasswordFragmentBinding
import br.com.rodrigolmti.dashboard.domain.model.PasswordModel
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.ui.DashboardActivity
import br.com.rodrigolmti.uikit.hide
import br.com.rodrigolmti.uikit.show
import javax.inject.Inject

class PasswordFragment : BaseFragment(), NavigationMode by DefaultNavigationMode {

    @Inject
    internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val binding by viewBinding {
        PasswordFragmentBinding.inflate(layoutInflater)
    }

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
    ): View = binding.root

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as DashboardActivity).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFields()
    }

    private fun setupFields() {
        binding.toolbar.onBackClick = {
            findNavController().popBackStack()
        }

        binding.btnAction.setOnClickListener {
            binding.content.hideKeyboard()
            viewModel.dispatchViewAction(
                PasswordAction.SavePassword(
                    id = savedPasswordModel?.id,
                    label = binding.etPasswordLabel.text.toString(),
                    login = binding.etPasswordLogin.text.toString(),
                    password = binding.etPassword.text.toString(),
                    strength = binding.colorIndicator.selectedPosition
                )
            )
        }

        binding.etPassword.addTextChangedListener(TextChangedWatcher {
            viewModel.dispatchViewAction(PasswordAction.CalculatePasswordStrength(it))
        })

        binding.imgShuffle.setOnClickListener {
            viewModel.dispatchViewAction(PasswordAction.ShufflePassword)
        }

        binding.toolbar.title = getString(R.string.password_fragment_title_save)

        passwordModel?.let { model ->
            binding.toolbar.title = getString(R.string.password_fragment_title_save)
            binding.btnAction.text = getString(R.string.action_save)
            binding.etPassword.setText(model.password)
        }

        savedPasswordModel?.let { model ->
            binding.toolbar.title = getString(R.string.password_fragment_title_edit)
            binding.btnAction.text = getString(R.string.action_edit)
            binding.etPasswordLabel.setText(model.label)
            binding.etPasswordLogin.setText(model.login)
            binding.etPassword.setText(model.password)
            binding.toolbar.onMenuClick = {
                viewModel.dispatchViewAction(PasswordAction.DeleteSavedPassword(model))
            }
        }

        observeChanges()
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun observeChanges() {
        viewModel.viewState.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                PasswordViewState.State.IDLE -> {
                    toIdleState()
                }
                PasswordViewState.State.LOADING -> {
                    toLoadingState()
                }
            }.exhaustive
        })
        viewModel.viewState.action.observe(viewLifecycleOwner, { action ->
            when (action) {
                is PasswordViewState.Action.ShowPasswordStrength -> {
                    binding.colorIndicator.selectedPosition = action.strength
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
                    binding.etPassword.setText(action.password)
                }
                is PasswordViewState.Action.ShowGeneratePasswordError -> {
                    showSnackBar(getString(R.string.password_fragment_generate_password_error))
                }
            }.exhaustive
        })
    }

    private fun toIdleState() {
        binding.btnAction.show()
        binding.content.show()
        binding.lottie.hide()
    }

    private fun toLoadingState() {
        binding.btnAction.hide()
        binding.content.hide()
        binding.lottie.show()
    }
}