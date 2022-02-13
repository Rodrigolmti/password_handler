package br.com.rodrigolmti.authentication.domain.error

sealed  class AuthenticationError {
    object DeletePinError : AuthenticationError()
    object SavePinError : AuthenticationError()
    object CheckPinError : AuthenticationError()
    object EncodePasswordError : AuthenticationError()
    object DecodePasswordError : AuthenticationError()
}