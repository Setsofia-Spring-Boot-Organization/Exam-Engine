package com.examengine.exam_engine.exceptions

data class MyExceptionPayload(
    private val status: Int,
    private val message: String
)