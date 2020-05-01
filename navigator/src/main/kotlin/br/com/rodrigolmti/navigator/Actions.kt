package br.com.rodrigolmti.navigator

import android.content.Context
import android.content.Intent

object Actions {

    fun openAuthentication(context: Context) =
        internalIntent(context, "br.com.rodrigolmti.authentication.open")

    fun openDashboard(context: Context) =
        internalIntent(context, "br.com.rodrigolmti.dashboard.open")

    private fun internalIntent(context: Context, action: String) =
        Intent(action).setPackage(context.packageName)
}

