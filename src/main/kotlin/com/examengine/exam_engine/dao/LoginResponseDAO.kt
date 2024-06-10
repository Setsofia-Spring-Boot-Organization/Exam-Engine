package com.examengine.exam_engine.dao

class LoginResponseDAO (
    private val status: Int,
    private val message: String,
    private val username: String,
    private val token: String
) {
    fun getStatus() : Int {
        return this.status
    }

    fun getMessage() : String {
        return this.message
    }

    fun getToken() : String {
        return this.token
    }

    fun getUsername() : String {
        return this.username
    }
}
