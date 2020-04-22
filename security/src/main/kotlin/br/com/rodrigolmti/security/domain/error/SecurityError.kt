package br.com.rodrigolmti.security.domain.error

sealed class SecurityError {
    object GenerateKeyError : SecurityError()
    object GenerateVectorError : SecurityError()
    object SaveKeyError : SecurityError()
    object SaveVectorError : SecurityError()
    object GenerateKeysError : SecurityError()
    object EncodePasswordError : SecurityError()
    object DecodePasswordError : SecurityError()
}