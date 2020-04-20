package br.com.rodrigolmti.dashboard.ui.password_generator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.rodrigolmti.core_android.base.BaseFragment
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.core_android.extensions.hide
import br.com.rodrigolmti.core_android.extensions.show
import br.com.rodrigolmti.core_android.navigation_modes.ImmersiveNavigationMode
import br.com.rodrigolmti.core_android.navigation_modes.NavigationMode
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.domain.model.PasswordModel
import br.com.rodrigolmti.dashboard.ui.DashboardActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.password_generator_fragment.*

class PasswordGeneratorFragment : BaseFragment(), NavigationMode by ImmersiveNavigationMode {

    private val viewModel by lazy { getViewModel(PasswordGeneratorViewModel::class.java) }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dispatchViewAction(PasswordGeneratorAction.InitView)
        setupFields()
    }

    private fun setupFields() {
        btnAction.setOnClickListener {
            if (recyclerView.visibility == View.VISIBLE) {
                viewModel.dispatchViewAction(PasswordGeneratorAction.ClearModel)
                recyclerView.adapter = null
                toIdleState()
                return@setOnClickListener
            }
            viewModel.dispatchViewAction(
                PasswordGeneratorAction.GeneratePassword(
                    passwordLength = etPasswordLength.text.toString().toInt(),
                    passwordNumber = etPasswordCount.text.toString().toInt(),
                    isUpperCase = sUppercaseLetters.isChecked,
                    isLowerCase = sSmallLetters.isChecked,
                    isNumbers = sNumbers.isChecked,
                    isSpecialChars = sCharacters.isChecked
                )
            )
        }
        setupRecyclerView()
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.viewState.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                PasswordGeneratorViewState.State.IDLE -> toIdleState()
                PasswordGeneratorViewState.State.LOADING -> toLoadingState()
            }.exhaustive
        })
        viewModel.viewState.action.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                is PasswordGeneratorViewState.Action.ShowPasswordList -> {
                    btnAction.text = getText(R.string.password_generator_fragment_clear)
                    setupAdapter(action.passwords)
                    toListSate()
                }
                PasswordGeneratorViewState.Action.ShowError -> {
                    Snackbar.make(
                        viewHeader,
                        getString(R.string.password_generator_fragment_error), Snackbar.LENGTH_SHORT
                    ).show()
                }
                PasswordGeneratorViewState.Action.ShowNoParamSelectedError -> {
                    Snackbar.make(
                        viewHeader,
                        getString(R.string.password_generator_fragment_no_params),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                PasswordGeneratorViewState.Action.ShowNumberTooSmallError -> {
                    Snackbar.make(
                        viewHeader,
                        getString(R.string.password_generator_fragment_too_small),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }.exhaustive
        })
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)
            val dividerItemDecoration = DividerItemDecoration(
                recyclerView.context,
                LinearLayoutManager.VERTICAL
            )
            ContextCompat.getDrawable(requireContext(), R.drawable.item_divisor)?.let { drawable ->
                dividerItemDecoration.setDrawable(drawable)
            }
            recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }

    private fun setupAdapter(passwords: List<PasswordModel>) {
        recyclerView.apply {
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

    private fun RecyclerView.handleCopyClick(model: PasswordModel) {
        (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).also { clipboard ->
            val clip = ClipData.newPlainText("", model.password)
            clipboard.setPrimaryClip(clip)
        }
        Snackbar.make(
            viewHeader,
            getString(R.string.password_generator_fragment_copy_message), Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun toLoadingState() {
        lottie.show()
        content.hide()
        btnAction.hide()
        recyclerView.hide()
    }

    private fun toIdleState() {
        lottie.hide()
        content.show()
        btnAction.show()
        recyclerView.hide()
        btnAction.text = getText(R.string.password_generator_fragment_generate)
    }

    private fun toListSate() {
        lottie.hide()
        content.hide()
        btnAction.show()
        recyclerView.show()
    }
}