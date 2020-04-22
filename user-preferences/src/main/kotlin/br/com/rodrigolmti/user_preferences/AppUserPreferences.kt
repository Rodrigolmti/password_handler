package br.com.rodrigolmti.user_preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

private const val PREFERENCES_FILE_NAME = "password-keeper-user-preferences"

class AppUserPreferences @Inject constructor(
    private val app: Application
) : UserPreferences {

    private val sharedPreferences: SharedPreferences by lazy {
        app.getSharedPreferences(
            PREFERENCES_FILE_NAME,
            Context.MODE_PRIVATE
        )
    }

    override fun putString(key: String, value: String): Boolean = with(sharedPreferences.edit()) {
        putString(key, value)
        commit()
    }

    override fun getString(key: String): String? = sharedPreferences.getString(key, null)

    override fun putBoolean(key: String, value: Boolean): Boolean = with(sharedPreferences.edit()) {
        putBoolean(key, value)
        commit()
    }

    override fun getBoolean(key: String): Boolean = sharedPreferences.getBoolean(key, false)
}