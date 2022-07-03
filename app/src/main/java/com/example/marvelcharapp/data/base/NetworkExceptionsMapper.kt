package com.example.marvelcharapp.data.base

import java.lang.Exception
import javax.inject.Inject

class NetworkExceptionsMapper @Inject constructor() {
    fun mapNetworkException(code: Int, message: String?) : APIError =
        when (code) {
            500 -> APIError.InternalServerError(message ?: "")
            503 -> APIError.ServiceUnavailable(message ?: "")
            422 -> APIError.UnprocessableEntity(message ?: "")
            404 -> APIError.NotFound(message ?: "")
            408 -> APIError.TimeOut(message ?: "")

            else -> APIError.UnmappedError(code, message ?: "")
        }

    fun mapException(e: Exception): APIError = APIError.UnmappedError(-1, e.message ?: "")
}