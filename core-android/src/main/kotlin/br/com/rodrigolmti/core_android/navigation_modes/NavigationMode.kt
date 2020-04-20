package br.com.rodrigolmti.core_android.navigation_modes

interface NavigationMode {
    fun isImmersive(): Boolean
}

object ImmersiveNavigationMode :
    NavigationMode {
    override fun isImmersive(): Boolean = true
}

object DefaultNavigationMode :
    NavigationMode {
    override fun isImmersive(): Boolean = false
}