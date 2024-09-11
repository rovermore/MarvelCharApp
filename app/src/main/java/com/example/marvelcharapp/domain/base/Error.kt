package com.example.marvelcharapp.domain.base

sealed class Error(override val message: String): Throwable(message) {
    class UncompletedOperation(message: String = "") : Error(message)
    class OperationCompletedWithError(message: String = ""): Error(message)
    class Unauthorized(message: String = ""): Error(message)
    class Unmapped(message: String = "", ): Error(message)
    class ConnectionError(message: String = ""): Error(message)

}