package br.com.rodrigolmti.security.biometric

import android.content.DialogInterface
import androidx.fragment.app.FragmentManager
import br.com.rodrigolmti.core_android.extensions.hideKeyboard
import br.com.rodrigolmti.core_android.extensions.setStateExpanded
import br.com.rodrigolmti.security.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

typealias ButtonAction = () -> Unit

abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    override fun onStart() {
        super.onStart()
        setStateExpanded()
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        view?.hideKeyboard()

        super.onDismiss(dialog)
    }

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, BottomSheetDialogFragment::javaClass.name)
    }
}