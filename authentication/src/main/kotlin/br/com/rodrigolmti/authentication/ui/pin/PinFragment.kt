package br.com.rodrigolmti.authentication.ui.pin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.rodrigolmti.authentication.R
import br.com.rodrigolmti.authentication.ui.AuthenticationActivity
import br.com.rodrigolmti.core_android.base.BaseFragment
import kotlinx.android.synthetic.main.pin_fragment.*

class PinFragment : BaseFragment() {

    private val viewModel by lazy { getViewModel(PinViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.pin_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as AuthenticationActivity).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pinKeyboardWidget.onPadKeyPressed = { pin ->
            etPin.setText(pin)
        }

        pinKeyboardWidget.onOkPressed = { pin ->

        }
    }
}