package br.com.rodrigolmti.uikit.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import br.com.rodrigolmti.ui_kit.R
import br.com.rodrigolmti.uikit.hide
import br.com.rodrigolmti.uikit.show
import kotlinx.android.synthetic.main.pad_key_widget.view.*

class PadKeyWidget : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setupView(attrs)
    }

    private fun setupView(attrs: AttributeSet? = null) {
        inflate(context, R.layout.pad_key_widget, this)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.PadKeyWidget)

            text = typedArray.getString(R.styleable.PadKeyWidget_padText)
            icon = typedArray.getResourceId(R.styleable.PadKeyWidget_padIcon, 0)

            typedArray.recycle()
        }
    }

    var text: String? = null
        set(text) {
            if (text.isNullOrEmpty()) tvText.hide() else tvText.show()
            tvText.text = text
            field = text
        }

    var icon: Int = 0
        set(value) {
            if (value > 0) {
                imgIcon.setImageResource(value)
                imgIcon.show()
            } else {
                imgIcon.hide()
            }
            field = value
        }
}