package com.examengine.exam_engine.exceptions

import com.examengine.exam_engine.enums.Reasons
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MyExceptions::class)
    fun handleGlobalException(myException: MyExceptions): ResponseEntity<Any> {
        val reason: Reasons = myException.getReason()
        val status: HttpStatus

        when (reason) {
            Reasons.INPUT_FIELDS_MUST_NOT_BE_EMPTY,
            Reasons.USER_ALREADY_EXISTS-> status =
                HttpStatus.BAD_REQUEST
        }
        val myExceptionPayload = MyExceptionPayload(
            reason.label,
            status
        )

        return ResponseEntity(myExceptionPayload, status)
    }
}
