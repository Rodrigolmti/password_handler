package br.com.rodrigolmti.uikit.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import br.com.rodrigolmti.ui_kit.R
import br.com.rodrigolmti.ui_kit.databinding.PadKeyWidgetBinding
import br.com.rodrigolmti.uikit.hide
import br.com.rodrigolmti.uikit.show

class PadKeyWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding =
        PadKeyWidgetBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .also { addView(it.root) }

    init {
        setupView(attrs)
    }

    private fun setupView(attrs: AttributeSet? = null) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.PadKeyWidget)

            text = typedArray.getString(R.styleable.PadKeyWidget_padText)
            icon = typedArray.getResourceId(R.styleable.PadKeyWidget_padIcon, 0)

            typedArray.recycle()
        }
    }

    var text: String? = null
        set(text) {
            if (text.isNullOrEmpty()) binding.tvText.hide() else binding.tvText.show()
            binding.tvText.text = text
            field = text
        }

    var icon: Int = 0
        set(value) {
            if (value > 0) {
                binding.imgIcon.setImageResource(value)
                binding.imgIcon.show()
            } else {
                binding.imgIcon.hide()
            }
            field = value
        }
}