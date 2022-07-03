package com.example.marvelcharapp.data.base

import javax.inject.Inject
import com.example.marvelcharapp.domain.base.Error

class APIErrorMapper @Inject constructor() {

    fun map(apiError: APIError): Error = when (apiError) {
        is APIError.InternalServerError -> Error.UncompletedOperation(apiError.message)
        is APIError.ServiceUnavailable -> Error.UncompletedOperation(apiError.message)
        is APIError.UnauthorizedError -> Error.Unauthorized(apiError.message)
        is APIError.NotFound -> Error.OperationCompletedWithError(apiError.message)
        is APIError.TimeOut -> Error.ConnectionError(apiError.message)
        else -> Error.Unmapped(apiError.message)
    }
}