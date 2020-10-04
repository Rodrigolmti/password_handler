package br.com.rodrigolmti.navigator

import android.content.Context
import android.content.Intent

const val AUTHENTICATION_ACTION = "br.com.rodrigolmti.authentication.open"
const val DASHBOARD_ACTION = "br.com.rodrigolmti.dashboard.open"

object Actions {

    fun openAuthentication(context: Context) =
        internalIntent(context, AUTHENTICATION_ACTION)

    fun openDashboard(context: Context) =
        internalIntent(context, DASHBOARD_ACTION)

    private fun internalIntent(context: Context, action: String) =
        Intent(action).setPackage(context.packageName)
}

