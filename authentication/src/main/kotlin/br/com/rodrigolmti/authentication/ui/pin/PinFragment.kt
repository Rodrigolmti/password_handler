package br.com.rodrigolmti.authentication.ui.pin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import br.com.rodrigolmti.authentication.databinding.PinFragmentBinding
import br.com.rodrigolmti.authentication.ui.AuthenticationActivity
import br.com.rodrigolmti.core_android.base.BaseFragment
import br.com.rodrigolmti.core_android.view_binding_delegate.viewBinding
import javax.inject.Inject

class PinFragment : BaseFragment() {

    @Inject
    internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val binding by viewBinding {
        PinFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<PinViewModel> { viewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as AuthenticationActivity).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pinKeyboardWidget.onPadKeyPressed = { pin ->
            binding.etPin.setText(pin)
        }

        binding.pinKeyboardWidget.onOkPressed = { pin ->

        }
    }
}