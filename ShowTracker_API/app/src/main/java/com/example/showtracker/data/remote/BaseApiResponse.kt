package com.example.showtracker.data.remote

import com.example.showtracker.utils.NetworkResult
import retrofit2.Response
import timber.log.Timber

abstract class BaseApiResponse {

    suspend fun <T,R> safeApiCall(apiCall: suspend () -> Response<R>,transform :(R) -> T ): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(transform(body))
                }
            }
            return NetworkResult.Error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            Timber.e(e.message, e.stackTrace)
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }
}