package br.com.rodrigolmti.dashboard.domain.error

sealed class DashboardError {
    object GeneratePasswordError : DashboardError()
    object InsertSavedPasswordError : DashboardError()
    object GetAllSavedPasswordsError : DashboardError()
    object UpdateSavesPasswordError : DashboardError()
    object DeleteSavedPasswordError : DashboardError()
}