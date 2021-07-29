package br.com.rodrigolmti.uikit.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import br.com.rodrigolmti.ui_kit.databinding.PinKeyboardWidgetBinding

class PinKeyboardWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding =
        PinKeyboardWidgetBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .also { addView(it.root) }

    init {
        setupView()
    }

    private fun setupView() {
        binding.padClear.setOnClickListener {
            if (stringBuilder.isNotEmpty()) {
                stringBuilder.deleteCharAt(stringBuilder.length - 1)
                sendPadKeyPressed()
            }
        }
        binding.padOk.setOnClickListener {
            onOkPressed?.invoke(stringBuilder.toString())
        }

        configureTextButtons(
            binding.padOne,
            binding.padTwo,
            binding.padThree,
            binding.padFour,
            binding.padFive,
            binding.padSix,
            binding.padSeven,
            binding.padEight,
            binding.padNine,
            binding.padZero,
        )
    }

    private fun configureTextButtons(vararg padKeys: PadKeyWidget) {
        padKeys.forEach { padKey ->
            padKey.setOnClickListener {
                stringBuilder.append(padKey.text)
                sendPadKeyPressed()
            }
        }
    }

    private val stringBuilder = StringBuilder()

    var onPadKeyPressed: ((String) -> Unit)? = null
    var onOkPressed: ((String) -> Unit)? = null

    private fun sendPadKeyPressed() {
        onPadKeyPressed?.invoke(
            List(stringBuilder.length) { "*" }.joinToString().replace(
                ",",
                ""
            ).trim()
        )
    }
}