package com.devileya.ulos.utils

/**
 * Created by Arif Fadly Siregar 30/08/20.
 */
sealed class UseCaseResult<out T : Any?> {
    class Success<out T : Any>(val data: T) : UseCaseResult<T>()
    class Error(val exception: Throwable) : UseCaseResult<Nothing>()
    class MessageError(val message: String) : UseCaseResult<Nothing>()
}