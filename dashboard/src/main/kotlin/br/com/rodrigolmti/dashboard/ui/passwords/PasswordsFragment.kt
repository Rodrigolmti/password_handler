package br.com.rodrigolmti.dashboard.ui.passwords

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.rodrigolmti.core_android.base.BaseFragment
import br.com.rodrigolmti.core_android.extensions.copyToClipboard
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.core_android.navigation_modes.ImmersiveNavigationMode
import br.com.rodrigolmti.core_android.navigation_modes.NavigationMode
import br.com.rodrigolmti.core_android.view_binding_delegate.viewBinding
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.databinding.PasswordsFragmentBinding
import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel
import br.com.rodrigolmti.dashboard.ui.DashboardActivity
import br.com.rodrigolmti.dashboard.ui.passwords.PasswordsViewState.State.*
import br.com.rodrigolmti.uikit.hide
import br.com.rodrigolmti.uikit.show
import javax.inject.Inject

class PasswordsFragment : BaseFragment(), NavigationMode by ImmersiveNavigationMode {

    @Inject
    internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val binding by viewBinding {
        PasswordsFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<PasswordsViewModel> { viewModelProviderFactory }

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
        viewModel.dispatchViewAction(PasswordsAction.Init)
        setupFields()
    }

    private fun setupFields() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.dispatchViewAction(PasswordsAction.FilterPasswords(binding.etSearch.text.toString()))
            }
            true
        }

        binding.etSearch.addTextChangedListener {
            viewModel.dispatchViewAction(PasswordsAction.FilterPasswords(it.toString()))
        }

        binding.imgSearch.setOnClickListener {
            viewModel.dispatchViewAction(PasswordsAction.FilterPasswords(binding.etSearch.text.toString()))
        }

        binding.fBtnAdd.setOnClickListener {
            PasswordsFragmentDirections.actionPasswordsToPassword().also { navDirection ->
                findNavController().navigate(navDirection)
            }
        }
        setupRecyclerView()
        observeChanges()
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

    private fun observeChanges() {
        viewModel.viewState.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                IDLE -> {
                    toIdleState()
                }
                LOADING -> {
                    toLoadingState()
                }
                ERROR -> {
                    showSnackBar(getString(R.string.generic_error))
                }
                EMPTY_LIST -> {
                    toEmptyState()
                }
            }.exhaustive
        })
        viewModel.viewState.action.observe(viewLifecycleOwner, { action ->
            when (action) {
                is PasswordsViewState.Action.ShowSavedPasswordList -> {
                    setupAdapter(action.passwords)
                }
                is PasswordsViewState.Action.GetSavedPasswordsError -> {

                }
            }.exhaustive
        })
    }

    private fun setupAdapter(passwords: List<SavedPasswordModel>) {
        binding.recyclerView.apply {
            adapter = SavedPasswordsAdapter(
                password = passwords,
                onItemClick = {
                    PasswordsFragmentDirections.actionPasswordsToPassword(
                        savedPasswordModel = it
                    ).also { navDirection ->
                        findNavController().navigate(navDirection)
                    }
                },
                onCopyLoginClick = { model ->
                    if (model.login.isNullOrEmpty()) {
                        showSnackBar(getString(R.string.passwords_fragment_copy_login_error_message))
                        return@SavedPasswordsAdapter
                    }

                    requireContext().copyToClipboard(model.login)
                    showSnackBar(getString(R.string.passwords_fragment_copy_login_message))
                },
                onCopyPasswordClick = { model ->
                    requireContext().copyToClipboard(model.password)
                    showSnackBar(getString(R.string.passwords_fragment_copy_password_message))
                })
        }
    }

    private fun toIdleState() {
        binding.lottie.hide()
        binding.recyclerView.show()
        binding.imgVoid.hide()
        binding.tvVoid.hide()
    }

    private fun toLoadingState() {
        binding.lottie.show()
        binding.recyclerView.hide()
        binding.imgVoid.hide()
        binding.tvVoid.hide()
    }

    private fun toEmptyState() {
        binding.lottie.hide()
        binding.recyclerView.hide()
        binding.imgVoid.show()
        binding.tvVoid.show()
    }
}