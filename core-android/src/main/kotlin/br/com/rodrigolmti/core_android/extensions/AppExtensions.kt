package br.com.rodrigolmti.core_android.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

val <T> T.exhaustive: T get() = this

fun Context.copy(context: String) {
    (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).also { clipboard ->
        val clip = ClipData.newPlainText("", context)
        clipboard.setPrimaryClip(clip)
    }
}