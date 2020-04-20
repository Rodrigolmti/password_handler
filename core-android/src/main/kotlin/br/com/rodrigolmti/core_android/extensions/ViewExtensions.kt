package br.com.rodrigolmti.core_android.extensions

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun View.setMarginBottom(margin: Int) {
    (this.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        setMargins(leftMargin, topMargin, rightMargin, margin)
    }
}
