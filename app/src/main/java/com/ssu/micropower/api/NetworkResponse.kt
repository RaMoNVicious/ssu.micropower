package com.ssu.micropower.api

import androidx.annotation.Keep


sealed class Result<T> {
    data class Success<T>(val value: T) : Result<T>()
    data class Failure<T>(val throwable: Throwable) : Result<T>()
}

@Keep
sealed class Failure : Exception() {
    class AuthNeeded : Failure()

    class AuthExpired : Failure()

    class ServerError(val messages: String) : Failure()

    data object NoConnection : Failure()

    class Unknown(val messages: String = "Unknown error") : Failure()

    data object NoData : Failure()
}

inline fun <L> Result<L>.onSuccess(fn: (success: L) -> Unit): Result<L> =
    apply { if (this is Result.Success) fn(value) }

inline fun <L> Result<L>.onFailure(fn: (failure: Throwable) -> Unit): Result<L> =
    apply { if (this is Result.Failure) fn(throwable) }

fun Throwable.getMessages(): String =
    when (this) {
        is Failure.AuthExpired -> "Session Expired!"
        is Failure.ServerError -> this.messages
        is Failure.NoConnection -> "Connection problem"
        is Failure.NoData -> "No Data"
        is Failure.Unknown -> this.messages
        else -> "Unknown"
    }
