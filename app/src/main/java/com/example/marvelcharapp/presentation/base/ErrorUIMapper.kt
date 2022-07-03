package com.example.marvelcharapp.presentation.base

import com.example.marvelcharapp.domain.base.Error
import javax.inject.Inject

class ErrorUIMapper @Inject constructor() {

    fun map(error: Error) = when (error) {
        is Error.ConnectionError -> ErrorUI.ConnectionError(error.message)
        is Error.UncompletedOperation -> ErrorUI.GenericError(error.message)
        is Error.OperationCompletedWithError -> ErrorUI.GenericError(error.message)
        is Error.Unauthorized -> ErrorUI.UnauthorizedError(error.message)
        is Error.Unmapped -> ErrorUI.UnmappedError(error.message)
    }
}