package com.example.marvelcharapp.data.base

sealed class APIError(val code: Int, val message: String) {
    class InternalServerError(message: String): APIError(500, message)
    class ServiceUnavailable(message: String): APIError(503, message)
    class UnprocessableEntity(message: String): APIError(422, message)
    class NotFound(message: String): APIError(404, message)
    class UnauthorizedError(message: String): APIError(403, message)
    class TimeOut(message: String): APIError(408, message)

    class UnmappedError(code: Int, message: String): APIError(code, message)
}