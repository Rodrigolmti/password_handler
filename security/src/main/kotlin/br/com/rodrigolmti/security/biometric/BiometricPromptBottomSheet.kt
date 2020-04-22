package br.com.rodrigolmti.security.biometric

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.UiThread
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.CancellationSignal
import androidx.fragment.app.FragmentManager
import br.com.rodrigolmti.security.R
import br.com.rodrigolmti.security.domain.model.BiometricEvent
import kotlinx.android.synthetic.main.biometric_prompt_bottom_sheet.*

typealias BiometricEventListener = (event: BiometricEvent) -> Unit

class BiometricPromptBottomSheet private constructor(
    private val builder: Builder
) : BaseBottomSheet() {

    class Builder(
        private val fragmentManager: FragmentManager,
        val title: CharSequence? = null,
        val subtitle: CharSequence? = null,
        val description: CharSequence? = null,
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
        description = builder.description
    }

    private var title: CharSequence?
        get() = item_title.text
        set(value) {
            item_title.text = value
        }

    private var subtitle: CharSequence?
        get() = item_subtitle.text
        set(value) {
            item_subtitle.text = value
            item_subtitle.visibility = View.VISIBLE
        }

    private var description: CharSequence?
        get() = item_description.text
        set(value) {
            item_description.text = value
            item_description.visibility = View.VISIBLE
        }

    private var biometricIcon: Int = 0
        set(@DrawableRes value) {
            value.takeIf { it != 0 }?.let {
                img_fingerprint.setImageResource(it)
            }

            field = value
        }

    var stateIcon: BiometricIconView.State
        get() = img_fingerprint.state
        set(value) {
            img_fingerprint.setState(value)
        }


    var status: CharSequence?
        get() = item_status.text
        set(value) {
            item_status.text = value
        }

    var statusColor: Int
        get() = item_status.currentTextColor
        set(@ColorRes value) {
            item_status.setTextColor(AppCompatResources.getColorStateList(requireContext(), value))
        }

    override fun onCancel(dialog: DialogInterface) {
        builder.biometricEventListener?.invoke(BiometricEvent.AuthenticationCancelled)
        builder.cancellationSignal?.cancel()
        super.onCancel(dialog)
    }

    @UiThread
    fun initialStatus() {
        context?.let {
            stateIcon = BiometricIconView.State.ON
            status = it.getString(R.string.biometric_sensor_touch)
            statusColor = R.color.biometric_status
        }
    }
}