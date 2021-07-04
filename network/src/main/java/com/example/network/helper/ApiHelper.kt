package com.example.network.helper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import okio.IOException
import retrofit2.HttpException

sealed class ResultWrapper<out T> {
    object Loading : ResultWrapper<Nothing>()
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(
        val code: Int? = null,
        val exception: Exception? = null,
        val message: String? = null
    ) : ResultWrapper<Nothing>()

    data class NetworkError(val exception: Exception? = null, val message: String? = null) : ResultWrapper<Nothing>()
}

suspend fun <T> safeApiCallFlow(apiCall: suspend () -> T): Flow<ResultWrapper<T>> {
    return flow {
        emit(
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError(throwable, throwable.localizedMessage)
                    is HttpException -> {
                        val code = throwable.code()
                        ResultWrapper.GenericError(code, throwable, throwable.localizedMessage)
                    }
                    else -> {
                        ResultWrapper.GenericError(null, null, "UnKnow Error")
                    }
                }
            }
        )
    }.onStart { ResultWrapper.Loading }
}