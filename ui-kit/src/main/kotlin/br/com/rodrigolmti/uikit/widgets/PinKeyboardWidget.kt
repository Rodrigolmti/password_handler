package br.com.rodrigolmti.uikit.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import br.com.rodrigolmti.ui_kit.R
import kotlinx.android.synthetic.main.pin_keyboard_widget.view.*

class PinKeyboardWidget : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setupView()
    }

    private fun setupView() {
        inflate(context, R.layout.pin_keyboard_widget, this)

        padClear.setOnClickListener {
            if (stringBuilder.isNotEmpty()) {
                stringBuilder.deleteCharAt(stringBuilder.length - 1)
                sendPadKeyPressed()
            }
        }
        padOk.setOnClickListener {
            onOkPressed?.invoke(stringBuilder.toString())
        }

        configureTextButtons(
            padOne, padTwo, padThree, padFour, padFive, padSix, padSeven, padEight, padNine, padZero
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