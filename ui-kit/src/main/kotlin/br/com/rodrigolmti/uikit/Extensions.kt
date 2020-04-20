package br.com.rodrigolmti.uikit

import android.content.res.Resources

inline val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()
inline val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

inline val Float.dp: Float get() = (this / Resources.getSystem().displayMetrics.density)
inline val Float.px: Float get() = (this * Resources.getSystem().displayMetrics.density)