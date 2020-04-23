package br.com.rodrigolmti.security.biometric

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.UiThread
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.CancellationSignal
import androidx.fragment.app.FragmentManager
import br.com.rodrigolmti.core_android.extensions.hideKeyboard
import br.com.rodrigolmti.core_android.extensions.setStateExpanded
import br.com.rodrigolmti.security.R
import br.com.rodrigolmti.security.domain.model.BiometricEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.biometric_prompt_bottom_sheet.*

typealias BiometricEventListener = (event: BiometricEvent) -> Unit

class BiometricPromptBottomSheet private constructor(
    private val builder: Builder
) : BottomSheetDialogFragment() {

    class Builder(
        private val fragmentManager: FragmentManager,
        val title: CharSequence? = null,
        val subtitle: CharSequence? = null,
        val biometricEventListener: BiometricEventListener? = null,
        val cancellationSignal: CancellationSignal? = null
    ) {
        @UiThread
        fun build(): BiometricPromptBottomSheet {
            return BiometricPromptBottomSheet(
                this
            )
        }

        @UiThread
        fun show(): BiometricPromptBottomSheet {
            val dialog = build()
            dialog.show(fragmentManager)
            return dialog
        }
    }

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.biometric_prompt_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialStatus()
        title = builder.title
        subtitle = builder.subtitle
    }

    private var title: CharSequence?
        get() = tvTitle.text
        set(value) {
            tvTitle.text = value
        }

    private var subtitle: CharSequence?
        get() = tvSubtitle.text
        set(value) {
            tvSubtitle.text = value
            tvSubtitle.visibility = View.VISIBLE
        }

    var status: CharSequence?
        get() = tvStatus.text
        set(value) {
            tvStatus.text = value
        }

    var statusColor: Int
        get() = tvStatus.currentTextColor
        set(@ColorRes value) {
            tvStatus.setTextColor(AppCompatResources.getColorStateList(requireContext(), value))
        }

    override fun onCancel(dialog: DialogInterface) {
        builder.biometricEventListener?.invoke(BiometricEvent.AuthenticationCancelled)
        builder.cancellationSignal?.cancel()
        super.onCancel(dialog)
    }

    @UiThread
    fun initialStatus() {
        context?.let {
            status = it.getString(R.string.biometric_sensor_touch)
            statusColor = R.color.biometric_status
        }
    }
}