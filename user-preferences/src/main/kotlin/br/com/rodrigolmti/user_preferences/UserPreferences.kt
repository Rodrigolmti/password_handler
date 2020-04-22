package br.com.rodrigolmti.user_preferences

interface UserPreferences {

    fun putString(key: String, value: String): Boolean

    fun getString(key: String): String?

    fun putBoolean(key: String, value: Boolean): Boolean

    fun getBoolean(key: String): Boolean
}