package br.com.rodrigolmti.user_preferences

interface UserPreferences {

    fun putString(key: String, value: String): Boolean
    fun getString(key: String): String?
}