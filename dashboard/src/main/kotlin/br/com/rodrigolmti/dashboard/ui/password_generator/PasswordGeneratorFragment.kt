package br.com.rodrigolmti.dashboard.ui.password_generator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.rodrigolmti.core_android.base.BaseFragment
import br.com.rodrigolmti.core_android.extensions.copyToClipboard
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.core_android.extensions.hideKeyboard
import br.com.rodrigolmti.core_android.navigation_modes.ImmersiveNavigationMode
import br.com.rodrigolmti.core_android.navigation_modes.NavigationMode
import br.com.rodrigolmti.core_android.view_binding_delegate.viewBinding
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.databinding.PasswordGeneratorFragmentBinding
import br.com.rodrigolmti.dashboard.domain.model.PasswordModel
import br.com.rodrigolmti.dashboard.ui.DashboardActivity
import br.com.rodrigolmti.uikit.hide
import br.com.rodrigolmti.uikit.show
import javax.inject.Inject

class PasswordGeneratorFragment : BaseFragment(), NavigationMode by ImmersiveNavigationMode {

    @Inject
    internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val binding by viewBinding {
        PasswordGeneratorFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<PasswordGeneratorViewModel> { viewModelProviderFactory }

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
        viewModel.dispatchViewAction(PasswordGeneratorAction.InitView)
        setupFields()
    }

    private fun setupFields() {
        binding.btnAction.setOnClickListener {
            binding.viewHeader.hideKeyboard()
            if (binding.recyclerView.visibility == View.VISIBLE) {
                viewModel.dispatchViewAction(PasswordGeneratorAction.ClearModel)
                binding.recyclerView.adapter = null
                toIdleState()
                return@setOnClickListener
            }
            viewModel.dispatchViewAction(
                PasswordGeneratorAction.GeneratePassword(
                    passwordLength = binding.etPasswordLength.text.toString().toInt(),
                    passwordNumber = binding.etPasswordCount.text.toString().toInt(),
                    isUpperCase = binding.sUppercaseLetters.isChecked,
                    isLowerCase = binding.sSmallLetters.isChecked,
                    isNumbers = binding.sNumbers.isChecked,
                    isSpecialChars = binding.sCharacters.isChecked
                )
            )
        }
        setupRecyclerView()
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.viewState.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                PasswordGeneratorViewState.State.IDLE -> toIdleState()
                PasswordGeneratorViewState.State.LOADING -> toLoadingState()
            }.exhaustive
        })
        viewModel.viewState.action.observe(viewLifecycleOwner, { action ->
            when (action) {
                is PasswordGeneratorViewState.Action.ShowPasswordList -> {
                    binding.btnAction.text = getText(R.string.password_generator_fragment_clear)
                    setupAdapter(action.passwords)
                    toListSate()
                }
                PasswordGeneratorViewState.Action.ShowError -> {
                    showSnackBar(getString(R.string.password_generator_fragment_error))
                }
                PasswordGeneratorViewState.Action.ShowNoParamSelectedError -> {
                    showSnackBar(getString(R.string.password_generator_fragment_no_params))
                }
                PasswordGeneratorViewState.Action.ShowNumberTooSmallError -> {
                    showSnackBar(getString(R.string.password_generator_fragment_too_small))
                }
            }.exhaustive
        })
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            val dividerItemDecoration = DividerItemDecoration(
                binding.recyclerView.context,
                LinearLayoutManager.VERTICAL
            )
            ContextCompat.getDrawable(requireContext(), R.drawable.item_divisor)?.let { drawable ->
                dividerItemDecoration.setDrawable(drawable)
            }
            binding.recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }

    private fun setupAdapter(passwords: List<PasswordModel>) {
        binding.recyclerView.apply {
            adapter = PasswordAdapter(
                password = passwords,
                onSaveClick = {
                    PasswordGeneratorFragmentDirections.actionPasswordGeneratorToPassword(
                        passwordModel = it
                    ).also { navDirection ->
                        findNavController().navigate(navDirection)
                    }
                },
                onCopyClick = { model ->
                    handleCopyClick(model)
                })
        }
    }

    private fun handleCopyClick(model: PasswordModel) {
        requireContext().copyToClipboard(model.password)
        showSnackBar(getString(R.string.password_generator_fragment_copy_message))
    }

    private fun toLoadingState() {
        binding.lottie.show()
        binding.content.hide()
        binding.btnAction.hide()
        binding.recyclerView.hide()
    }

    private fun toIdleState() {
        binding.lottie.hide()
        binding.content.show()
        binding.btnAction.show()
        binding.recyclerView.hide()
        binding.btnAction.text = getText(R.string.password_generator_fragment_generate)
    }

    private fun toListSate() {
        binding.lottie.hide()
        binding.content.hide()
        binding.btnAction.show()
        binding.recyclerView.show()
    }
}