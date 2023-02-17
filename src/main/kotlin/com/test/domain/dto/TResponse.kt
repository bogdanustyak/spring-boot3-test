package com.test.domain.dto

data class TResponse<T>(
    val message: String? = null,
    val data: T? = null
)
