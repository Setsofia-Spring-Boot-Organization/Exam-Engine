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
        val status: HttpStatus
        val reason: Reasons = myException.getReason()

        status = when (reason) {
            Reasons.INPUT_FIELDS_MUST_NOT_BE_EMPTY,
            Reasons.USER_ALREADY_EXISTS,
            Reasons.USER_NOT_FOUND,
            Reasons.ANSWERS_NOT_SUBMITTED,
            Reasons.INVALID_PASSWORD -> HttpStatus.BAD_REQUEST

            Reasons.ONLY_ADMINS_CAN_PERFORM_THIS_ACTION,
            Reasons.ONLY_STUDENTS_CAN_PERFORM_THIS_ACTION-> HttpStatus.FORBIDDEN

            Reasons.NO_QUESTIONS_FOUND -> HttpStatus.NOT_FOUND
        }

        val myExceptionPayload = MyExceptionPayload(
            status.value(),
            reason.label
        )
        return ResponseEntity(myExceptionPayload, status)
    }
}
