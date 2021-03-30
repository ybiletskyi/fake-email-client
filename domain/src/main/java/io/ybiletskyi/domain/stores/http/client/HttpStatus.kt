package io.ybiletskyi.domain.stores.http.client

internal sealed class HttpStatus {
    data class Success(val json: String): HttpStatus()
    data class Error(val error: String?): HttpStatus()
}