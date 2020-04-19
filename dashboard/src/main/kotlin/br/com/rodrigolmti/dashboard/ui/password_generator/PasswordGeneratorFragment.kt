package br.com.rodrigolmti.dashboard.ui.password_generator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import br.com.rodrigolmti.core_android.base.BaseFragment
import br.com.rodrigolmti.core_android.extensions.exhaustive
import br.com.rodrigolmti.core_android.extensions.hide
import br.com.rodrigolmti.core_android.extensions.show
import br.com.rodrigolmti.dashboard.R
import br.com.rodrigolmti.dashboard.ui.DashboardActivity
import kotlinx.android.synthetic.main.password_generator_fragment.*

class PasswordGeneratorFragment : BaseFragment() {

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
        setupFields()
    }

    private fun setupFields() {
        btnAction.setOnClickListener {
            if (recyclerView.visibility == View.VISIBLE) {
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
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.viewState.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                PasswordGeneratorState.State.IDLE -> toIdleState()
                PasswordGeneratorState.State.LOADING -> toLoadingState()
            }.exhaustive
        })
        viewModel.viewState.action.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                is PasswordGeneratorState.Action.ShowPasswordList -> {
                    toListSate()
                    recyclerView.apply {
                        setHasFixedSize(true)
                        adapter = PasswordAdapter(
                            password = action.passwords,
                            onAddClick = {},
                            onCopyClick = {})
                    }
                    btnAction.setText(getText(R.string.password_generator_clear))
                }
                PasswordGeneratorState.Action.ShowError -> {

                }
            }.exhaustive
        })
    }

    private fun toLoadingState() {
        lottie.show()
        content.hide()
        recyclerView.hide()
    }

    private fun toIdleState() {
        lottie.hide()
        content.show()
        recyclerView.hide()
    }

    private fun toListSate() {
        lottie.hide()
        content.hide()
        recyclerView.show()
    }
}