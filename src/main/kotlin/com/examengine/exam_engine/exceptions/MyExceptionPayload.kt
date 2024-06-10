package com.examengine.exam_engine.exceptions

class MyExceptionPayload(
    private val status: Int,
    private val message: String
) {
    fun getMessage() : String {
        return this.message
    }

    fun getStatus() : Int {
        return this.status
    }
}