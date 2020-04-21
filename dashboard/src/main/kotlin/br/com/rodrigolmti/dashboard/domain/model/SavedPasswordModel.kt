package br.com.rodrigolmti.dashboard.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SavedPasswordModel(
    val id: Int? = null,
    val password: String,
    val strength: Int = 0,
    val login: String? = null,
    val label: String
) : Parcelable {

    val obfuscatedPassword: String
        get() = List(password.length) { "*" }.joinToString().replace(",", "").trim()
}