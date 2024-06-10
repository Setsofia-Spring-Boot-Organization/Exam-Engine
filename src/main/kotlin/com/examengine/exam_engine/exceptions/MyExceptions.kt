package com.examengine.exam_engine.exceptions

import com.examengine.exam_engine.enums.Reasons

class MyExceptions(reasons: Reasons) : Exception(reasons.toString().replace("_", " ")) {
    private val reason: Reasons = reasons

    fun getReason() : Reasons {
        return this.reason
    }
}