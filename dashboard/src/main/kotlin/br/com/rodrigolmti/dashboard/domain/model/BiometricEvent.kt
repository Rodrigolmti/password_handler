package br.com.rodrigolmti.dashboard.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class BiometricEvent : Parcelable {

    @Parcelize
    object BiometricPermissionNotGranted : BiometricEvent()

    @Parcelize
    object AuthenticationCancelled : BiometricEvent()

    @Parcelize
    object AuthenticationSucceeded : BiometricEvent()

    @Parcelize
    object SdkVersionNotSupported : BiometricEvent()

    @Parcelize
    object BiometricNotAvailable : BiometricEvent()

    @Parcelize
    object BiometricNotSupported : BiometricEvent()

    @Parcelize
    object AuthenticationFailed : BiometricEvent()

    @Parcelize
    data class AuthenticationHelp(
        val helpString: CharSequence,
        val helpCode: Int
    ) : BiometricEvent()

    @Parcelize
    data class AuthenticationError(
        val errorString: CharSequence,
        val errorCode: Int
    ) : BiometricEvent()
}