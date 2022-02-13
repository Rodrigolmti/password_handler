package br.com.rodrigolmti.authentication.domain.error

sealed  class AuthenticationError {
    object DeletePinError : AuthenticationError()
}