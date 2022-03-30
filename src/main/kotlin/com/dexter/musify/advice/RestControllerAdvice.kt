package com.dexter.musify.advice

import com.dexter.musify.exception.MusicBrainzClientException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestControllerAdvice: ResponseEntityExceptionHandler() {

    @ExceptionHandler(MusicBrainzClientException::class)
    protected fun handleHttpException(ex: MusicBrainzClientException): ResponseEntity<Any> {
        return ResponseEntity("Unable to Fetch Artist Details", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}