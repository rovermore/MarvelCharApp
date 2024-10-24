package com.example.marvelcharapp.presentation.base

sealed class ErrorUI (val message: String) {
    data object None: ErrorUI("")
    class GenericError(msg: String): ErrorUI(msg)
    class UnauthorizedError(msg: String): ErrorUI(msg)
    class UnmappedError(msg: String): ErrorUI(msg)
    class ConnectionError(msg: String): ErrorUI(msg)
}