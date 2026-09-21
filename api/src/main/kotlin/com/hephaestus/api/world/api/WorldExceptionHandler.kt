package com.hephaestus.api.world.api

import com.hephaestus.api.world.application.exceptions.InvalidWorldException
import com.hephaestus.api.world.application.exceptions.WorldNotFoundException
import com.hephaestus.api.world.application.exceptions.WorldNotUploadedException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class WorldExceptionHandler {
    @ExceptionHandler(WorldNotFoundException::class)
    fun handleWorldNotFoundException(e: WorldNotFoundException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)

        problemDetail.title = "World not found"
        problemDetail.detail = e.message

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail)
    }

    @ExceptionHandler(WorldNotUploadedException::class)
    fun handleWorldNotUploadedException(e: WorldNotUploadedException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)

        problemDetail.title = "World not uploaded"
        problemDetail.detail = e.message

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail)
    }

    @ExceptionHandler(InvalidWorldException::class)
    fun handleInvalidWorldException(e: InvalidWorldException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)

        problemDetail.title = "Invalid World"
        problemDetail.detail = e.message

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail)
    }
}