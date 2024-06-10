package com.examengine.exam_engine.exceptions

import org.springframework.http.HttpStatus

class MyExceptionPayload(
    private val message: String,
    private val status: HttpStatus
) {
    fun getMessage() : String {
        return this.message
    }

    fun getStatus() : HttpStatus {
        return this.status
    }
}