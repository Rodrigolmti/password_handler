package br.com.rodrigolmti.navigator

import android.content.Context
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class AuthenticationOrigin : Parcelable {
    SETTINGS, AUTHENTICATION, NONE
}

object Navigator {

    const val AuthenticationOriginKey = "AUTHENTICATION_ORIGIN_KEY"

    fun navigateToAuthentication(
        context: Context,
        origin: AuthenticationOrigin = AuthenticationOrigin.NONE
    ) {
        with(Actions.openAuthentication(context)) {
            putExtra(AuthenticationOriginKey, origin as Parcelable)
            context.startActivity(this)
        }
    }

    fun navigateToDashboard(
        context: Context
    ) {
        with(Actions.openDashboard(context)) {
            context.startActivity(this)
        }
    }
}