package br.com.rodrigolmti.uikit

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import br.com.rodrigolmti.ui_kit.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

typealias ButtonAction = () -> Unit

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun BottomSheetDialogFragment.setStateExpanded() {
    dialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)?.let {
        BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
    }
}

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