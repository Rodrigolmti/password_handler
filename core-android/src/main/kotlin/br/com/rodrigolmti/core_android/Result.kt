package br.com.rodrigolmti.core_android

sealed class Result<out D, out E> {
    data class Success<D>(val value: D) : Result<D, Nothing>()
    data class Error<E>(val value: E) : Result<Nothing, E>()

    inline fun handleResult(
        onSuccess: (result: D) -> Unit = {},
        onError: (error: E) -> Unit = {},
        onFinish: (D?) -> Unit = {}
    ): D? = when (this) {
        is Success -> {
            onSuccess(value)
            onFinish(value)
            value
        }
        is Error -> {
            onError(value)
            onFinish(null)
            null
        }
    }
}