package br.com.rodrigolmti.dashboard.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PasswordModel(
    val password: String,
    val score: Int
) : Parcelable