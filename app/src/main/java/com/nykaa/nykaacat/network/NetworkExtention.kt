package com.nykaa.nykaacat.network

import androidx.annotation.Keep
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any> safeApiCall(
    execute: suspend () -> Response<T>
): ApiResponse<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            ApiResponse.Success(body)
        } else {
            ApiResponse.Error.ResponseError(Pair(response.code().toString(), response.message()))
        }
    } catch (e: HttpException) {
        ApiResponse.Error.ApiError(Pair(e.code().toString(), e.message()))
    } catch (e: Exception) {
        ApiResponse.Exception(e)
    }
}


@Keep
sealed class ApiResponse<T> {
    class NOOP<T> : ApiResponse<T>()
    class Loading<T>() : ApiResponse<T>()
    class Success<T>(val data: T?) : ApiResponse<T>()
    sealed class Error<T>(val message: Pair<String?, String?>?, val data: T? = null) : ApiResponse<T>() {
        class ApiError<T>(message: Pair<String?, String?>?, data: T? = null) : Error<T>(message, data)
        class ResponseError<T>(message: Pair<String?, String?>?, data: T? = null) : Error<T>(message, data)
    }
    class Exception<T>(val exception: kotlin.Exception?) : ApiResponse<T>()
}