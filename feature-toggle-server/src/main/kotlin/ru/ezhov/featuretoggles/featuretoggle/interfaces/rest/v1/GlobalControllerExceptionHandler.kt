package ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model.InternalErrorResponseDto

@RestControllerAdvice
class GlobalControllerExceptionHandler {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun internalError(ex: Exception): ResponseEntity<InternalErrorResponseDto> =
            ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(InternalErrorResponseDto(message = ex.message ?: "Error"))
}