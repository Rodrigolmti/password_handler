package br.com.rodrigolmti.uikit.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import br.com.rodrigolmti.ui_kit.R
import br.com.rodrigolmti.ui_kit.databinding.ToolbarBinding
import br.com.rodrigolmti.uikit.hide
import br.com.rodrigolmti.uikit.show

class ToolbarWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding =
        ToolbarBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .also { addView(it.root) }

    init {
        applyStyle()
    }

    private fun applyStyle() {
        binding.imgBack.setOnClickListener {
            onBackClick?.invoke()
        }
        binding.imgMenu.setOnClickListener {
            onMenuClick?.invoke()
        }
    }

    var title: String? = null
        get() = binding.tvTitle.text.toString()
        set(text) {
            binding.tvTitle.text = text
            field = text
        }

    var onBackClick: (() -> Unit)? = null

    var onMenuClick: (() -> Unit)? = null
        set(value) {
            if (value != null) binding.imgMenu.show() else binding.imgMenu.hide()
            field = value
        }
}