package br.com.rodrigolmti.uikit.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import br.com.rodrigolmti.ui_kit.R
import br.com.rodrigolmti.uikit.px

class ColorIndicatorWidget : View {

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

    private var itemRectF = RectF()
    private var backgroundPaint = Paint()
    private lateinit var colorArray: IntArray

    private var barPadding: Float = 3f.px

    var selectedPosition: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    private fun applyStyle(attrs: AttributeSet, defStyleAttr: Int = 0, defStyleRes: Int = 0) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ColorIndicatorWidget,
            defStyleAttr,
            defStyleRes
        )

        barPadding = typedArray.getDimension(R.styleable.ColorIndicatorWidget_barPadding, 3f.px)

        colorArray = typedArray.getResourceId(
            R.styleable.ColorIndicatorWidget_colorArray,
            R.array.indicator_colors
        ).let {
            resources.getIntArray(it)
        }

        selectedPosition = typedArray.getInt(R.styleable.ColorIndicatorWidget_selectedPosition, 0)

        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        val numItems = colorArray.size - 1
        val totalBarPaddingWidth = barPadding * (numItems - 1)
        val width = (width - totalBarPaddingWidth) / numItems
        var xStart = 0f
        var xEnd = xStart + width

        for (i in 0 until numItems) {

            backgroundPaint.color = when {
                i >= selectedPosition -> colorArray[0]
                selectedPosition > numItems -> colorArray[numItems]
                else -> colorArray[selectedPosition]
            }

            itemRectF.set(xStart, 0f, xEnd, height.toFloat())
            canvas?.drawRoundRect(itemRectF, height.toFloat(), height.toFloat(), backgroundPaint)

            xStart = xEnd + barPadding
            xEnd = xStart + width
        }

        super.onDraw(canvas)
    }
}