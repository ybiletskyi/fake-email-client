package io.ybiletskyi.domain.http.client

sealed class HttpStatus {
    data class Success(val json: String): HttpStatus()
    data class Error(val error: String?): HttpStatus()
}