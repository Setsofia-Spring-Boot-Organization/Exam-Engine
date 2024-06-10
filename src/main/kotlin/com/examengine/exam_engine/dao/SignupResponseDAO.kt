package com.examengine.exam_engine.dao

data class SignupResponseDAO (
    private val status: Int,
    private val message: String
) {
    fun getStatus() : Int {
        return this.status
    }

    fun getMessage() : String {
        return this.message
    }
}