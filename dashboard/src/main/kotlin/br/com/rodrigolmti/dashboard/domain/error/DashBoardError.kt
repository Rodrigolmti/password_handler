package br.com.rodrigolmti.dashboard.domain.error

sealed class DashBoardError {
    object GeneratePasswordError : DashBoardError()
    object InsertSavedPasswordError : DashBoardError()
    object GetAllSavedPasswordsError : DashBoardError()
    object UpdateSavesPasswordError : DashBoardError()
    object DeleteSavedPasswordError : DashBoardError()
}