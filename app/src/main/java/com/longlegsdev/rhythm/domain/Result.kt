package com.longlegsdev.rhythm.domain

import android.R.attr.action
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val msg: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success $data"
            is Failure -> "Failure $msg"
            Loading -> "Loading..."
        }
    }
}

fun <T, R> Flow<Result<T>>.mapper(transform: (T) -> R): Flow<Result<R>> {
    return this.map {
        it.result(transform)
    }
}

fun <T, R> Result<T>.result(transform: (T) -> R): Result<R> {
    return when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Failure -> Result.Failure(msg)
        Result.Loading -> Result.Loading
    }
}


fun <T> Flow<Result<T>>.doOnSuccess(action: suspend (T) -> Unit): Flow<Result<T>> =
    transform { result ->
        if (result is Result.Success) {
            action(result.data)
        }
        return@transform emit(result)
    }

fun <T> Flow<Result<T>>.doOnFailure(action: suspend (Throwable) -> Unit): Flow<Result<T>> =
    transform { result ->
        if (result is Result.Failure) {
            action(result.msg)
        }
        return@transform emit(result)
    }

fun <T> Flow<Result<T>>.doOnLoading(action: suspend () -> Unit): Flow<Result<T>> =
    transform { result ->
        if (result is Result.Loading) {
            action()
        }
        return@transform emit(result)
    }