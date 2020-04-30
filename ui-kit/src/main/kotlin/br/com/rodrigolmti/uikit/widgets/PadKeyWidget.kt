package br.com.rodrigolmti.uikit.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import br.com.rodrigolmti.ui_kit.R
import br.com.rodrigolmti.uikit.hide
import br.com.rodrigolmti.uikit.show
import kotlinx.android.synthetic.main.pad_key_widget.view.*
import kotlinx.android.synthetic.main.toolbar.view.*

class PadKeyWidget : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        applyStyle(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        applyStyle(attrs, defStyleAttr)
    }

    private fun applyStyle(attrs: AttributeSet, defStyleAttr: Int = 0, defStyleRes: Int = 0) {
        inflate(context, R.layout.pad_key_widget, this)

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PadKeyWidget,
            defStyleAttr,
            defStyleRes
        )

        text = typedArray.getString(R.styleable.PadKeyWidget_padText)
        icon = typedArray.getInteger(R.styleable.PadKeyWidget_padIcon, 0)

        pad.setOnClickListener { onPadClick?.invoke() }

        typedArray.recycle()
    }

    var onPadClick: (() -> Unit)? = null

    var text: String? = null
        set(text) {
            if (text.isNullOrEmpty()) tvText.hide() else tvText.show()
            tvText.text = text
            field = text
        }

    var icon: Int? = null
        set(value) {
            value?.let {
                imgBack.setImageResource(it)
                imgBack.show()
            } ?: run {
                imgBack.hide()
            }
            field = value
        }
}