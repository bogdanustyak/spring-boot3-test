package com.test.presentation.rest

import com.test.domain.dto.TResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class TExceptionHandler : ResponseEntityExceptionHandler() {

    // TODO add any exception handler here

    @SuppressWarnings("UnusedPrivateMember")
    @ExceptionHandler(value = [Exception::class])
    fun handleException(req: HttpServletRequest, e: Exception): ResponseEntity<TResponse<Nothing>> {
        logger.debug(e.localizedMessage)

        return ResponseEntity(TResponse(message = e.localizedMessage), HttpStatus.BAD_REQUEST)
    }
}
