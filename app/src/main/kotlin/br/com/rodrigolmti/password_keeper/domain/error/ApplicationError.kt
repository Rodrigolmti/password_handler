package br.com.rodrigolmti.password_keeper.domain.error

sealed class ApplicationError {
    object GenerateKeyError : ApplicationError()
    object GenerateVectorError : ApplicationError()
    object SaveKeyError : ApplicationError()
    object GetKeyError : ApplicationError()
    object SaveVectorError : ApplicationError()
    object GetVectorError : ApplicationError()
}