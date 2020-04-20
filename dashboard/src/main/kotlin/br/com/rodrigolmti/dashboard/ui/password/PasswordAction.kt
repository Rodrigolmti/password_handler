package br.com.rodrigolmti.dashboard.ui.password

import br.com.rodrigolmti.dashboard.domain.model.SavedPasswordModel

internal sealed class PasswordAction {

    object ShufflePassword : PasswordAction()
    data class DeleteSavedPassword(val model: SavedPasswordModel) : PasswordAction()
    data class CalculatePasswordStrength(val password: String) : PasswordAction()
    data class SavePassword(
        val id: Int? = null,
        val label: String,
        val login: String? = null,
        val password: String,
        val strength: Int
    ) : PasswordAction()
}